package dev.blynchik.magicRangers.model.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class GuestUser extends User implements AppPrincipal {
    private final Long id;

    public GuestUser(Long id, String username, Collection<? extends GrantedAuthority> authorities) {
        super(username, "", authorities); // пароль пустой
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }
}
