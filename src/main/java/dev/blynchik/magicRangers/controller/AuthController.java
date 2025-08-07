package dev.blynchik.magicRangers.controller;

import dev.blynchik.magicRangers.controller.rout.MainPageRoutes;
import dev.blynchik.magicRangers.model.auth.AuthUser;
import dev.blynchik.magicRangers.service.model.AppCharacterService;
import dev.blynchik.magicRangers.service.model.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static dev.blynchik.magicRangers.controller.rout.AuthRoutes.LOGIN;
import static dev.blynchik.magicRangers.controller.rout.AuthRoutes.LOGOUT;
import static dev.blynchik.magicRangers.controller.rout.CharacterPageRoutes.CHARACTER;
import static dev.blynchik.magicRangers.controller.rout.CharacterPageRoutes.NEW;

@Controller
@Slf4j
public class AuthController {

    private final AppCharacterService characterService;
    private final AppUserService userService;

    public AuthController(AppCharacterService characterService,
                          AppUserService userService) {
        this.characterService = characterService;
        this.userService = userService;
    }

    @GetMapping
    public String login(@AuthenticationPrincipal AuthUser authUser) {
        log.info("Request GET to {} with default realisation", LOGIN);
        if (authUser != null) {
            boolean userExists = userService.existsById(authUser.getId());
            if (!userExists) {
                SecurityContextHolder.clearContext();
                return "redirect:" + LOGIN;
            }
            Long userId = authUser.getId();
            boolean hasCharacter = characterService.existsByAppUserId(userId);
            log.info("Authenticated user {} has character: {}", userId, hasCharacter);
            return hasCharacter ? "redirect:" + MainPageRoutes.MAIN
                    : "redirect:" + CHARACTER + NEW;
        }
        return "/login";
    }

    @GetMapping(LOGOUT)
    public void logout() {
        log.info("Request to {} with default realisation", LOGOUT);
    }
}
