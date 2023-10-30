package it.almaviva.eai.ljsa.sdk.core.security;

import it.almaviva.eai.ljsa.sdk.core.model.LjsaUserDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
@ConditionalOnProperty(
        value = "ljsa.profilemanager.enabled",
        havingValue = "false"
)
public class LjsaBasicFilter extends LjsaFilter {

    @Override
    protected AbstractAuthenticationToken giveMePrincipal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, LjsaUserDTO ljsaUserDTO) {
        Collection<String> roles = (Collection<String>) ljsaUserDTO.getRoles();
        Map<String, ?> claims = ljsaUserDTO.getClaims();

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String role: roles) {
            authorities.add(new SimpleGrantedAuthority(role.toUpperCase()));
        }

        LjsaUser ljsaUser = new LjsaUser(ljsaUserDTO.getUsername(),
                "",true,true,true,true,
                authorities, roles, null, claims);

        return new UsernamePasswordAuthenticationToken(ljsaUser, null, authorities);
    }
}
