package dev.blynchik.magicRangers.controller;

import dev.blynchik.magicRangers.controller.rout.*;
import dev.blynchik.magicRangers.model.auth.AppPrincipal;
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
@RequestMapping(MenuRoutes.MENU)
public class MenuController {

    private final AppUserService appUserService;

    @Autowired
    public MenuController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }


    @GetMapping(MenuRoutes.TO_MAIN)
    public String redirectToMainPage(@AuthenticationPrincipal AppPrincipal authUser) {
        log.info("Redirect to main user id: {}", authUser.getId());
        return "redirect:" + MainPageRoutes.MAIN;
    }

    @GetMapping(MenuRoutes.NEW_CHAR)
    public String redirectToNewCharacter(@AuthenticationPrincipal AppPrincipal authUser) {
        log.info("Redirect to create new character user id: {}", authUser.getId());
        return "redirect:" + CharacterPageRoutes.CHARACTER + CharacterPageRoutes.NEW;
    }

    @GetMapping(MenuRoutes.NEW_EVENT)
    public String redirectToNewEvent(@AuthenticationPrincipal AppPrincipal authUser) {
        log.info("Redirect to create new event user id: {}", authUser.getId());
        return "redirect:" + EventPageRoutes.EVENT + EventPageRoutes.NEW;
    }

    @DeleteMapping(MenuRoutes.DEL_CHAR)
    public String deleteOwnCharacter(@AuthenticationPrincipal AppPrincipal authUser) {
        log.info("Deleting character by user id: {}", authUser.getId());
        appUserService.delete(authUser.getId());
        return "redirect:" + CharacterPageRoutes.CHARACTER + CharacterPageRoutes.NEW;
    }

    @DeleteMapping(MenuRoutes.DEL_USER)
    public String deleteOwnUser(@AuthenticationPrincipal AppPrincipal authUser,
                                HttpServletRequest request) {
        log.info("Deleting user id: {}", authUser.getId());
        appUserService.delete(authUser.getId());
        SecurityContextHolder.clearContext();
        request.getSession().invalidate();
        return "redirect:" + AuthRoutes.LOGIN;
    }

    @GetMapping(MenuRoutes.TO_LOGOUT)
    public String logout(@AuthenticationPrincipal AppPrincipal authUser,
                         HttpServletRequest request) {
        log.info("Logout user id: {}", authUser.getId());
        SecurityContextHolder.clearContext();
        request.getSession().invalidate();
        return "redirect:" + AuthRoutes.LOGOUT;
    }
}
