package dev.blynchik.magicRangers.controller;

import dev.blynchik.magicRangers.model.auth.AuthUser;
import dev.blynchik.magicRangers.model.dto.CharacterRequest;
import dev.blynchik.magicRangers.model.dto.CharacterResponse;
import dev.blynchik.magicRangers.service.model.CharacterService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static dev.blynchik.magicRangers.controller.CharacterPageController.CHARACTER;

@Controller
@RequestMapping(CHARACTER)
@Slf4j
public class CharacterPageController {

    private final CharacterService characterService;
    public static final String CHARACTER = "/character";
    public static final String NEW = "/new";
    public static final String ID = "/{id}";
    public static final String MY = "/my";

    @Autowired
    public CharacterPageController(CharacterService characterService) {
        this.characterService = characterService;
    }

    /**
     * Создает нового персонажа и перенаправляет на страницу с собственным персонажем
     */
    @PostMapping
    public String create(@AuthenticationPrincipal AuthUser authUser,
                         @Valid @ModelAttribute("character") CharacterRequest dto,
                         RedirectAttributes redirectAttributes) {
        log.info("Request POST to {}", CHARACTER);
        CharacterResponse ch = characterService.createWithDto(authUser.getAppUser().getId(), dto);
        redirectAttributes.addFlashAttribute("character", ch);
        return "redirect:" + CHARACTER + MY;
    }

    /**
     * Возвращает страницу создания персонажа
     */
    @GetMapping(NEW)
    public String showCreate(Model model) {
        log.info("Request GET to {}", CHARACTER + NEW);
        model.addAttribute("character", new CharacterRequest());
        return "character/new";
    }

    /**
     * Возвращает страницу персонажа по id персонажа
     */
    @GetMapping(ID)
    public String get(@PathVariable Long id,
                      Model model) {
        log.info("Request GET to {}", CHARACTER + ID);
        CharacterResponse ch = characterService.getDtoById(id);
        model.addAttribute("character", ch);
        return "character/view";
    }

    /**
     * Возвращает страницу с собственным персонажем
     */
    @GetMapping(MY)
    public String getMy(@AuthenticationPrincipal AuthUser authUser,
                        Model model) {
        log.info("Request GET to {}", CHARACTER + MY);
        CharacterResponse ch = characterService.getDtoByAppUserId(authUser.getAppUser().getId());
        model.addAttribute("character", ch);
        return "character/view";
    }
}
