package it.almaviva.eai.ljsa.sdk.plugins.jwt.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import it.almaviva.eai.ljsa.sdk.core.model.LjsaUserDTO;
import it.almaviva.eai.ljsa.sdk.core.security.ILjsaAuthenticationFilter;
import it.almaviva.eai.ljsa.sdk.plugins.jwt.configuration.JwtConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component("jwt")
public class JwtFilter implements ILjsaAuthenticationFilter {

    @Autowired
    protected JwtConfigurationProperties properties;

    @Override
    public LjsaUserDTO doAuthentication(ServletRequest servletRequest, ServletResponse httpServletResponse) {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        final String token = httpServletRequest.getHeader("X-Auth-Token");
        final String issuer = properties.getIssuerKey();
        final String audience = properties.getAudienceKey();
        final String signingKey = properties.getSigningKey();

        DecodedJWT decodedJWT =  JWT.decode(token);
        String alg = decodedJWT.getAlgorithm();
        if(alg == null){
            throw new JWTVerificationException("Invalid alg in JOSE");
        }

        switch (alg){
            case "RS256":
                decodedJWT = getDecodedJWT(token, issuer, signingKey);
                break;

            case "HS256":
                decodedJWT = getDecodedJWT(token, issuer, audience, signingKey);
                break;

            default:
                break;
        }

        return getAuthCredentials(decodedJWT);
    }

    private DecodedJWT getDecodedJWT(String token, String issuer, String audience, String signingKey) {
        DecodedJWT decodedJWT;
        Algorithm algorithm = Algorithm.HMAC256(Base64.getUrlDecoder().decode(signingKey));
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).withAudience(audience).build();
        decodedJWT = verifier.verify(token);
        return decodedJWT;
    }

    private DecodedJWT getDecodedJWT(String token, String issuer, String signingKey) {
        DecodedJWT decodedJWT;
        RSAPublicKey publicKey = null;
        try {
            publicKey = exportPublicKey(signingKey);
        } catch (CertificateException e) {
            log.error(e.getMessage(), e);
        }
        Algorithm algorithm = Algorithm.RSA256(publicKey, null);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
        decodedJWT = verifier.verify(token);
        return decodedJWT;
    }

    private RSAPublicKey exportPublicKey(String signingKey) throws CertificateException {
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) factory
                .generateCertificate(new ByteArrayInputStream(
                        DatatypeConverter.parseBase64Binary(signingKey)));
        return (RSAPublicKey)cert.getPublicKey();
    }

    private LjsaUserDTO getAuthCredentials(DecodedJWT decodedJWT) {

        final String subject = decodedJWT.getClaim(properties.getSubjectKey()).asString();
        final Claim rolesClaim = decodedJWT.getClaim(properties.getRolesKey());

        List<String> jwtRolesList =null;
        if(rolesClaim.asString()!= null){
            jwtRolesList = Arrays.asList(rolesClaim.asString().split(","));
        }
        if(rolesClaim.asArray(String.class) != null){
            jwtRolesList = Arrays.asList(rolesClaim.asArray(String.class));
        }

        List<String> roleList = null;
        if(jwtRolesList != null) {
            jwtRolesList.stream().filter(s -> !s.startsWith("Application/")).collect(Collectors.toList());
        }

        Map<String, String> claims = decodedJWT.getClaims().entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().asString()));


        return  new LjsaUserDTO(subject, roleList, claims);
    }

}
