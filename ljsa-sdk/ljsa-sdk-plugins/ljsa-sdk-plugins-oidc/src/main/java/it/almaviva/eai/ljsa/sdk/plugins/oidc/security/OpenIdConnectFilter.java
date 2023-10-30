package it.almaviva.eai.ljsa.sdk.plugins.oidc.security;

import com.auth0.jwk.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import it.almaviva.eai.ljsa.sdk.core.model.LjsaUserDTO;
import it.almaviva.eai.ljsa.sdk.core.security.ILjsaAuthenticationFilter;
import it.almaviva.eai.ljsa.sdk.core.throwable.JWTHeaderException;
import it.almaviva.eai.ljsa.sdk.plugins.oidc.configuration.OpenIdConnectConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Primary
@Component("oidc")
public class OpenIdConnectFilter implements ILjsaAuthenticationFilter {

    @Autowired
    protected OpenIdConnectConfigurationProperties properties;

    @Override
    public LjsaUserDTO doAuthentication(ServletRequest servletRequest, ServletResponse httpServletResponse)  {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        final String token =  httpRequest.getHeader("X-Auth-Token");
        if(token == null || token.trim().isEmpty()){
            throw new JWTHeaderException("JWT in X-Auth-Token is null or empty", null);
        }
        log.debug("token > "+ token);

        final String issuer = properties.getIssuerKey();
        DecodedJWT decodedJWT =  JWT.decode(token);
        String kid = decodedJWT.getKeyId();
        log.debug("kid > "+ kid);

        Jwk jwk= null;
        try {
            jwk = getRSAKeyProvider(kid);
        } catch (MalformedURLException e) {
            log.error("MalformedURLException", e);
        } catch (JwkException e) {
            log.error("JwkException", e);
        }

        if(jwk == null){
            throw new JWTHeaderException("JWK is null", null);
        }

        Algorithm algorithm = null;
        try {
            algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
        } catch (InvalidPublicKeyException e) {
            log.error("InvalidPublicKeyException", e);
        }

        JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
        decodedJWT = verifier.verify(token);
        return getAuthCredentials(decodedJWT);
    }

    private Jwk getRSAKeyProvider(String kid) throws MalformedURLException, JwkException {
        JwkProvider jwkProvider = new JwkProviderBuilder(new URL(properties.getUrlJwkProvider()))
            .cached(10,24, TimeUnit.HOURS)
            .build();

        return jwkProvider.get(kid);
    }

    private LjsaUserDTO getAuthCredentials(DecodedJWT decodedJWT) {

        final String subject = decodedJWT.getClaim(properties.getSubjectKey()).asString();
        final Claim rolesClaim = decodedJWT.getClaim(properties.getRolesKey());

        List<String> jwtRolesList =null;
        if(rolesClaim.asString()!= null){
            jwtRolesList =Arrays.asList(rolesClaim.asString().split(","));
        }
        if(rolesClaim.asArray(String.class) != null){
            jwtRolesList = Arrays.asList(rolesClaim.asArray(String.class));
        }



        Map<String, Object> claims = new HashMap<>();
        for(Map.Entry<String, Claim> entry : decodedJWT.getClaims().entrySet()){
            String key = entry.getKey();
            Claim claim = entry.getValue();

            claims.put(key, claim.asString() != null ? claim.asString() : claim.asArray(String.class));
        }

        List<String> roleList = null;
        if(jwtRolesList != null) {
            roleList = jwtRolesList.stream().filter(s -> !s.startsWith("Application/")).collect(Collectors.toList());
        }

       return new LjsaUserDTO(subject, roleList,  claims);

    }

}
