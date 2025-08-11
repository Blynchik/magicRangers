package dev.blynchik.magicRangers.controller;

import dev.blynchik.magicRangers.controller.rout.AuthRoutes;
import dev.blynchik.magicRangers.controller.rout.CharacterPageRoutes;
import dev.blynchik.magicRangers.controller.rout.EventPageRoutes;
import dev.blynchik.magicRangers.controller.rout.MainPageRoutes;
import dev.blynchik.magicRangers.model.auth.AuthUser;
import dev.blynchik.magicRangers.service.model.AppUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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


    @GetMapping("/main")
    public String redirectToMainPage(@AuthenticationPrincipal AuthUser authUser) {
        log.info("Redirect to main user id: {}", authUser.getId());
        return "redirect:" + MainPageRoutes.MAIN;
    }

    @GetMapping("/new-character")
    public String redirectToNewCharacter(@AuthenticationPrincipal AuthUser authUser) {
        log.info("Redirect to create new character user id: {}", authUser.getId());
        return "redirect:" + CharacterPageRoutes.CHARACTER + CharacterPageRoutes.NEW;
    }

    @GetMapping("/new-event")
    public String redirectToNewEvent(@AuthenticationPrincipal AuthUser authUser) {
        log.info("Redirect to create new event user id: {}", authUser.getId());
        return "redirect:" + EventPageRoutes.EVENT + EventPageRoutes.NEW;
    }

    @DeleteMapping("/character")
    public String deleteOwnCharacter(@AuthenticationPrincipal AuthUser authUser) {
        log.info("Deleting character by user id: {}", authUser.getId());
        appUserService.delete(authUser.getId());
        return "redirect:" + CharacterPageRoutes.CHARACTER + CharacterPageRoutes.NEW;
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

    @GetMapping("/logout")
    public String logout(@AuthenticationPrincipal AuthUser authUser,
                         HttpServletRequest request) {
        log.info("Logout user id: {}", authUser.getId());
        SecurityContextHolder.clearContext();
        request.getSession().invalidate();
        return "redirect:" + AuthRoutes.LOGOUT;
    }
}
