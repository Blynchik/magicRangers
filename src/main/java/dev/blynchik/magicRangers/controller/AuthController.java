package dev.blynchik.magicRangers.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AuthController {

    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/logout";

    @GetMapping(LOGIN)
    public void login() {
        log.info("Request GET to {} with default realisation", LOGIN);
    }

    @GetMapping(LOGOUT)
    public void logout() {
        log.info("Request to {} with default realisation", LOGOUT);
    }
}
