package dev.blynchik.magicRangers.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OAuth2UserExtractors {

    @Bean("google")
    BaseOAuth2UserExtractor googleExtractor() {
        return new BaseOAuth2UserExtractor("sub", "email", "given_name", "family_name");
    }
}
