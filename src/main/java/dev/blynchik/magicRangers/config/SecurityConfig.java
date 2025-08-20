package dev.blynchik.magicRangers.config;

import dev.blynchik.magicRangers.config.auth.HasCharacterAuthenticationSuccessHandler;
import dev.blynchik.magicRangers.controller.rout.MainPageRoutes;
import dev.blynchik.magicRangers.model.auth.GuestUser;
import dev.blynchik.magicRangers.model.storage.Role;
import dev.blynchik.magicRangers.service.auth.OAuth2UserService;
import dev.blynchik.magicRangers.service.model.AppCharacterService;
import dev.blynchik.magicRangers.service.model.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final OAuth2UserService oAuth2UserService;
    private final AppCharacterService characterService;
    private final AppUserService appUserService;

    @Autowired
    public SecurityConfig(OAuth2UserService oAuth2UserService,
                          AppCharacterService characterService,
                          AppUserService appUserService) {
        this.oAuth2UserService = oAuth2UserService;
        this.characterService = characterService;
        this.appUserService = appUserService;
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HasCharacterAuthenticationSuccessHandler successHandler) throws Exception {
        return http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/", "/guest").permitAll();
                    auth.requestMatchers(MainPageRoutes.MAIN + "/**", "/menu/**").hasAnyRole(Role.USER.name(), Role.GUEST.name());
                    auth.anyRequest().hasRole(Role.USER.name());
                })
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .addLogoutHandler((request, response, authentication) -> {
                            if (authentication != null && authentication.getPrincipal() instanceof GuestUser guestUser) {
                                Long guestId = guestUser.getId();
                                characterService.deleteByAppUserId(guestId);
                                appUserService.delete(guestId);
                            }
                        })
                        .logoutSuccessUrl("/")
                )
                .oauth2Login(oauth -> oauth
                        .loginPage("/")
                        .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService))
                        .successHandler(successHandler)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }
}
