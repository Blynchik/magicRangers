package dev.blynchik.magicRangers.service.auth;

import dev.blynchik.magicRangers.config.auth.BaseOAuth2UserExtractor;
import dev.blynchik.magicRangers.model.auth.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthUserService extends DefaultOAuth2UserService {

    private final Map<String, BaseOAuth2UserExtractor> oAuth2UserExtractors;

    @Autowired
    public AuthUserService(Map<String, BaseOAuth2UserExtractor> oAuth2UserExtractors) {
        this.oAuth2UserExtractors = oAuth2UserExtractors;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String clientRegistrationId = userRequest.getClientRegistration().getRegistrationId();
        BaseOAuth2UserExtractor userExtractor = Optional.of(oAuth2UserExtractors.get(clientRegistrationId))
                .orElseThrow(() -> new OAuth2AuthenticationException("Unknown auth provider"));
        userExtractor.setOAuth2User(oAuth2User);
        String email = Optional.of(userExtractor.getEmail())
                .orElseThrow(() -> new OAuth2AuthenticationException(
                        "%s account does not contains email".formatted(clientRegistrationId)));
        return new AuthUser(oAuth2User);
    }
}
