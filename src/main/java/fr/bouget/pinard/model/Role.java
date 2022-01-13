package fr.bouget.pinard.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * User possible roles.
 */
public enum Role implements GrantedAuthority {

    ROLE_ADMIN, ROLE_CREATOR, ROLE_READER;

    @Override
    public String getAuthority() {
        return name();
    }
}
