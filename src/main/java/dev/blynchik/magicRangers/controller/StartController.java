package dev.blynchik.magicRangers.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StartController {

    @GetMapping("/")
    public String notSecured() {
        return "Unsafe";
    }

    @GetMapping("/secured")
    public String secured() {
        return "Hello";
    }
}
