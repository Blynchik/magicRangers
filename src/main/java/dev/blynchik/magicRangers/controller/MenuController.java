package dev.blynchik.magicRangers.controller;

import dev.blynchik.magicRangers.controller.rout.AuthRoutes;
import dev.blynchik.magicRangers.controller.rout.CharacterPageRoutes;
import dev.blynchik.magicRangers.model.auth.AuthUser;
import dev.blynchik.magicRangers.service.model.AppUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/menu")
public class MenuController {

    private final AppUserService appUserService;

    @Autowired
    public MenuController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @DeleteMapping("/user")
    public String deleteOwnUser(@AuthenticationPrincipal AuthUser authUser,
                                HttpServletRequest request) {
        log.info("Deleting user id: {}", authUser.getId());
        appUserService.delete(authUser.getId());
        SecurityContextHolder.clearContext();
        request.getSession().invalidate();
        return "redirect:" + AuthRoutes.LOGIN;
    }

    @DeleteMapping("/character")
    public String deleteOwnCharacter(@AuthenticationPrincipal AuthUser authUser) {
        log.info("Deleting character by user id: {}", authUser.getId());
        appUserService.delete(authUser.getId());
        return "redirect:" + CharacterPageRoutes.CHARACTER + CharacterPageRoutes.NEW;
    }
}
