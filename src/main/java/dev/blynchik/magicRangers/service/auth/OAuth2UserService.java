package dev.blynchik.magicRangers.service.auth;

import dev.blynchik.magicRangers.config.auth.BaseOAuth2UserExtractor;
import dev.blynchik.magicRangers.model.auth.AuthUser;
import dev.blynchik.magicRangers.model.storage.AppUser;
import dev.blynchik.magicRangers.service.model.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

import static dev.blynchik.magicRangers.exception.ExceptionMessage.*;

@Service
@Slf4j
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final Map<String, BaseOAuth2UserExtractor> oAuth2UserExtractors;
    private final AppUserService appUserService;

    @Autowired
    public OAuth2UserService(Map<String, BaseOAuth2UserExtractor> oAuth2UserExtractors,
                             AppUserService appUserService) {
        this.oAuth2UserExtractors = oAuth2UserExtractors;
        this.appUserService = appUserService;
    }

    /**
     * Метод загружает пользователя от провайдера OAuth2 и сохраняет нового пользователя в БД,
     * если пользователя с такой почтой нет. Для работы методы необходимо от провайдера иметь идентификатор провайдера,
     * уникальный идентификатор регистрируемого пользователя в среде провайдера и email регистрируемого пользователя
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("Attempt to load oAuth2 user");
        OAuth2User oAuth2User = loadUserFromProvider(userRequest);
        String oAuth2Provider = userRequest.getClientRegistration().getRegistrationId();
        BaseOAuth2UserExtractor userExtractor = Optional.ofNullable(oAuth2UserExtractors.get(oAuth2Provider))
                .orElseThrow(() -> new OAuth2AuthenticationException(
                        OAUTH2_UNKNOWN_PROVIDER.formatted(oAuth2Provider)));
        userExtractor.setOAuth2User(oAuth2User);
        String sub = Optional.ofNullable(userExtractor.getSub())
                .orElseThrow(() -> new OAuth2AuthenticationException(
                        OAUTH2_MISSING_SUB.formatted(oAuth2Provider)));
        String email = Optional.ofNullable(userExtractor.getEmail())
                .orElseThrow(() -> new OAuth2AuthenticationException(
                        OAUTH2_MISSING_EMAIL.formatted(oAuth2Provider)));
        AppUser appUser = appUserService.saveAppUserIfNotExist(oAuth2Provider, sub, email);
        return new AuthUser(oAuth2User, appUser);
    }

    // вынесено для возможности мокирования метода в тестах
    // нельзя сделать private, т.к. есть проблемы при мокировании, рефлексия здесь не поможет
    // можно решить перенеся класс тестирования в пакет с этим классом и пометить этот метод protected
    public OAuth2User loadUserFromProvider(OAuth2UserRequest userRequest) {
        return super.loadUser(userRequest);
    }
}
