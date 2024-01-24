package hexlet.code.app.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface BaseEntity {
    Collection<? extends GrantedAuthority> getAuthorities();
}
