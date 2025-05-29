package dev.blynchik.magicRangers.config.auth;

import dev.blynchik.magicRangers.model.auth.AuthUser;
import dev.blynchik.magicRangers.service.model.CharacterService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static dev.blynchik.magicRangers.controller.rout.CharacterPageRoutes.*;

@Component
@Slf4j
public class HasCharacterAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private CharacterService characterService;

    /**
     * При успешной аутентификации пользователя перенаправляем пользователя на разные ресурсы
     * Если у пользователя есть персонаж - на страницу персонажа
     * Если нет, то на страницу создания персонажа
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Long userId = ((AuthUser) authentication.getPrincipal()).getAppUser().getId();
        boolean hasCharacter = characterService.hasCharacter(userId);
        String targetUrl = hasCharacter ? CHARACTER + MY : CHARACTER + NEW;
        log.info("AppUser id {} has character: {}, and redirected: {}", userId, hasCharacter, targetUrl);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
