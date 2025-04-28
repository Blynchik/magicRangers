package dev.blynchik.magicRangers.config.auth;

import org.springframework.security.oauth2.core.user.OAuth2User;

public class BaseOAuth2UserExtractor {
    private OAuth2User oAuth2User;

    private final String sub;
    private final String email;
    private final String firstName;
    private final String lastName;

    public BaseOAuth2UserExtractor(String sub, String email, String firstName, String lastName) {
        this.sub = sub;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    protected <A> A get(String paramName) {
        return paramName == null ? null : oAuth2User.getAttribute(paramName);
    }

    public String getSub() {
        return get(sub);
    }

    public String getEmail() {
        return get(email);
    }

    public String getFirstName() {
        return get(firstName);
    }

    public String getLastName() {
        return get(lastName);
    }

    public void setOAuth2User(OAuth2User oAuth2User) {
        this.oAuth2User = oAuth2User;
    }
}
