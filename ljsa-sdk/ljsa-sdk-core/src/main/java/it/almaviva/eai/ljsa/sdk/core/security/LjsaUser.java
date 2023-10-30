package it.almaviva.eai.ljsa.sdk.core.security;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

@Getter
@EqualsAndHashCode(exclude = {"roles","groups","claims"}, callSuper=false)
public class LjsaUser extends User {

    private final Collection<? extends Serializable> roles;
    private final Collection<? extends Serializable> groups;
    private final Map<String, ?> claims;

    public LjsaUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, Collection<? extends Serializable> roles, Collection<? extends Serializable> groups, Map<String, ?> claims) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.roles = roles;
        this.groups = groups;
        this.claims = claims;
    }
}
