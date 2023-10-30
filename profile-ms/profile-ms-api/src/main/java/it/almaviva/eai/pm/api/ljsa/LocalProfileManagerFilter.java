package it.almaviva.eai.pm.api.ljsa;

import it.almaviva.eai.ljsa.sdk.core.model.LjsaUserDTO;
import it.almaviva.eai.ljsa.sdk.core.security.LjsaFilter;
import it.almaviva.eai.ljsa.sdk.core.security.LjsaUser;
import it.almaviva.eai.pm.core.service.IProfileService;
import it.almaviva.eai.pm.shared.dto.microservice.ProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class LocalProfileManagerFilter extends LjsaFilter {

    @Autowired
    private IProfileService iProfileService;

    @Value("${ljsa.profilemanager.micro-service-id}")
    private UUID microServiceId;

    @Override
    protected AbstractAuthenticationToken giveMePrincipal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, LjsaUserDTO ljsaUserDTO) {

        Collection<String> roles = (Collection<String>) ljsaUserDTO.getRoles();// WSO2 roles

        ProfileDTO profileDTO = iProfileService.getProfile(
            microServiceId, roles);

        List<String> rolesList = profileDTO.getRoles().stream().map(msRoleDTO -> msRoleDTO.getName()).collect(
            Collectors.toList());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String role: rolesList) {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.toUpperCase()));
        }

        Map<String, String> claims = (Map<String, String>) ljsaUserDTO.getClaims();
        String accountLocked = claims.getOrDefault("http://wso2.org/claims/identity/accountLocked" , "false");
        String accountDisabled = claims.getOrDefault("http://wso2.org/claims/identity/accountDisabled","false");

        LjsaUser ljsaUser = new LjsaUser(ljsaUserDTO.getUsername(),
                "",!Boolean.valueOf(accountDisabled),true,true,!Boolean.valueOf(accountLocked),
                authorities, profileDTO.getRoles(),profileDTO.getGroups(), ljsaUserDTO.getClaims());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(ljsaUser, null, ljsaUser.getAuthorities());
        return authenticationToken;
    }
}
