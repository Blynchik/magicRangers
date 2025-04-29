package dev.blynchik.magicRangers.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OAuth2UserExtractors {

    /**
     * Конфигурация извлечения данных пользователя Google из OAuth2 ответа.
     * Определяет какие атрибуты из ответа Google OAuth2 будут использоваться:
     * - 'sub' - уникальный идентификатор пользователя
     * - 'email' - email пользователя
     * - 'given_name' - имя пользователя
     * - 'family_name' - фамилия пользователя
     * Создает экземпляр BaseOAuth2UserExtractor с указанными ключами атрибутов,
     * которые соответствуют структуре ответа Google OAuth2 API.
     */
    @Bean("google")
    BaseOAuth2UserExtractor googleExtractor() {
        return new BaseOAuth2UserExtractor("sub", "email", "given_name", "family_name");
    }
}
