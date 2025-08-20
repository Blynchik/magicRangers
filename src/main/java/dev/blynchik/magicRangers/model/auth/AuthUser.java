package dev.blynchik.magicRangers.model.auth;

import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class AuthUser implements OAuth2User, AppPrincipal {
    private final OAuth2User oAuth2User;
    @Getter
    private final Long id;
    private final Collection<? extends GrantedAuthority> authorities;

    public AuthUser(@NonNull OAuth2User oAuth2User,
                    @NonNull Long id,
                    @NonNull Collection<? extends GrantedAuthority> authorities) {
        this.oAuth2User = oAuth2User;
        this.id = id;
        this.authorities = authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities; // Возвращаем наши authorities, а не из oAuth2User
    }

    @Override
    public String getName() {
        return oAuth2User.getName();
    }

    @Override
    public String toString() {
        return "AuthUser: id: %s".formatted(this.id);
    }
}
