package dev.blynchik.magicRangers.controller;

import dev.blynchik.magicRangers.controller.rout.MainPageRoutes;
import dev.blynchik.magicRangers.model.auth.AppPrincipal;
import dev.blynchik.magicRangers.model.auth.AuthUser;
import dev.blynchik.magicRangers.model.auth.GuestUser;
import dev.blynchik.magicRangers.model.storage.AppUser;
import dev.blynchik.magicRangers.model.storage.Role;
import dev.blynchik.magicRangers.service.model.AppCharacterService;
import dev.blynchik.magicRangers.service.model.AppUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;
import java.util.UUID;

import static dev.blynchik.magicRangers.controller.rout.AuthRoutes.*;
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
    public String login(@AuthenticationPrincipal AppPrincipal authUser) {
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

    @PostMapping(AS_GUEST)
    public String loginAsGuest(HttpServletRequest request) {
        AppUser guest = userService.saveAppUserIfNotExist(
                "own", "ownSub",
                UUID.randomUUID().toString().substring(0, 8) + "@example.ru",
                Set.of(Role.GUEST));
        characterService.createRandomCharacter(guest.getId());
        GuestUser guestUser = new GuestUser(guest.getId(), guest.getEmail(), guest.getRoles());
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(guestUser, null, guestUser.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);
        request.getSession(true).setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                context);
        return "redirect:" + MainPageRoutes.MAIN;
    }

    @GetMapping(LOGOUT)
    public void logout(@AuthenticationPrincipal AuthUser authUser,
                       HttpServletRequest request) {
        log.info("Logout user id: {}", authUser.getId());
        SecurityContextHolder.clearContext();
        request.getSession().invalidate();
    }
}
