package dev.blynchik.magicRangers.controller;

import dev.blynchik.magicRangers.controller.rout.AuthRoutes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static dev.blynchik.magicRangers.controller.rout.AuthRoutes.*;

@RestController
@Slf4j
public class AuthController {

    @GetMapping(LOGIN)
    public void login() {
        log.info("Request GET to {} with default realisation", LOGIN);
    }

    @GetMapping(LOGOUT)
    public void logout() {
        log.info("Request to {} with default realisation", LOGOUT);
    }
}
