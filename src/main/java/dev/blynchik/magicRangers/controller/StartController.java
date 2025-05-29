package dev.blynchik.magicRangers.controller;

import dev.blynchik.magicRangers.model.auth.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static dev.blynchik.magicRangers.controller.rout.StartRoutes.NOT_SECURED;
import static dev.blynchik.magicRangers.controller.rout.StartRoutes.SECURED;

@RestController
@Slf4j
public class StartController {

    @GetMapping(NOT_SECURED)
    public String notSecured() {
        log.info("Request GET to {}", NOT_SECURED);
        return "Unsafe";
    }

    @GetMapping(SECURED)
    public String secured(@AuthenticationPrincipal AuthUser authUser) {
        log.info("Request GET to {} by: {}", SECURED, authUser);
        return "Hello";
    }
}
