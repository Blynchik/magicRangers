package dev.blynchik.magicRangers.service.auth;

import dev.blynchik.magicRangers.config.auth.BaseOAuth2UserExtractor;
import dev.blynchik.magicRangers.model.auth.AuthUser;
import dev.blynchik.magicRangers.model.storage.AppUser;
import dev.blynchik.magicRangers.service.model.AppUserService;
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
    private final AppUserService appUserService;

    @Autowired
    public AuthUserService(Map<String, BaseOAuth2UserExtractor> oAuth2UserExtractors,
                           AppUserService appUserService) {
        this.oAuth2UserExtractors = oAuth2UserExtractors;
        this.appUserService = appUserService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String oAuth2Provider = userRequest.getClientRegistration().getRegistrationId();
        BaseOAuth2UserExtractor userExtractor = Optional.of(oAuth2UserExtractors.get(oAuth2Provider))
                .orElseThrow(() -> new OAuth2AuthenticationException(
                        "Unknown auth provider %s".formatted(oAuth2Provider)));
        userExtractor.setOAuth2User(oAuth2User);
        String sub = Optional.of(userExtractor.getSub())
                .orElseThrow(() -> new OAuth2AuthenticationException(
                        "%s account missing user ID (sub)".formatted(oAuth2Provider)));
        String email = Optional.of(userExtractor.getEmail())
                .orElseThrow(() -> new OAuth2AuthenticationException(
                        "%s account does not contains email".formatted(oAuth2Provider)));
        AppUser appUser = appUserService.saveAppUserIfNotExist(oAuth2Provider, sub, email);
        return new AuthUser(oAuth2User, appUser);
    }
}
