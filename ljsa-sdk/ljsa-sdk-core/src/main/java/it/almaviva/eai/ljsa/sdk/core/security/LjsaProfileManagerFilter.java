package it.almaviva.eai.ljsa.sdk.core.security;

import it.almaviva.eai.ljsa.sdk.core.model.LjsaUserDTO;
import it.almaviva.eai.ljsa.sdk.core.service.LjsaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(
        value = "ljsa.profilemanager.enabled",
        havingValue = "true"
)
public class LjsaProfileManagerFilter  extends LjsaFilter {

    @Autowired
    protected LjsaService ljsaService;

    @Override
    protected UsernamePasswordAuthenticationToken giveMePrincipal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, LjsaUserDTO ljsaUserDTO) {

        it.almaviva.eai.pm.core.grpc.Profile profile = ljsaService.getCachedProfile(ljsaUserDTO.getRoles());
        final List<it.almaviva.eai.pm.core.grpc.Group> groups = profile.getGroupsList();
        final List<it.almaviva.eai.pm.core.grpc.Role> roles = profile.getRolesList();

        List<String> rolesList = roles.stream().map(it.almaviva.eai.pm.core.grpc.Role::getName).collect(Collectors.toList());

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String role: rolesList) {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.toUpperCase()));
        }

        Map<String, String> claims = (Map<String, String>) ljsaUserDTO.getClaims();
        String accountLocked = claims.getOrDefault("http://wso2.org/claims/identity/accountLocked" , "false");
        String accountDisabled = claims.getOrDefault("http://wso2.org/claims/identity/accountDisabled","false");

        LjsaUser ljsaUser = new LjsaUser(ljsaUserDTO.getUsername(),
                "",!Boolean.valueOf(accountDisabled),true,true,!Boolean.valueOf(accountLocked),
                authorities, roles, groups, claims);

        return new UsernamePasswordAuthenticationToken(ljsaUser, null, ljsaUser.getAuthorities());
    }

}
