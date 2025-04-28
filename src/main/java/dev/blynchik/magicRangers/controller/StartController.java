package dev.blynchik.magicRangers.controller;

import dev.blynchik.magicRangers.model.auth.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class StartController {

    @GetMapping("/")
    public String notSecured() {
        log.info("Request GET to /");
        return "Unsafe";
    }

    @GetMapping("/secured")
    public String secured(@AuthenticationPrincipal AuthUser authUser) {
        log.info("Request GET to /secured by: {}", authUser);
        return "Hello";
    }
}
