package dev.blynchik.magicRangers.model.auth;

import dev.blynchik.magicRangers.model.storage.AppUser;
import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class AuthUser implements OAuth2User {

    private final OAuth2User oAuth2User;

    @Getter
    private final Long id;

    public AuthUser(@NonNull OAuth2User oAuth2User,
                    @NonNull Long id) {
        this.oAuth2User = oAuth2User;
        this.id = id;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
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
