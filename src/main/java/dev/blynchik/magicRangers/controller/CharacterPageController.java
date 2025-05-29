package dev.blynchik.magicRangers.controller;

import dev.blynchik.magicRangers.model.auth.AuthUser;
import dev.blynchik.magicRangers.model.dto.CharacterRequest;
import dev.blynchik.magicRangers.model.dto.CharacterResponse;
import dev.blynchik.magicRangers.service.model.CharacterService;
import dev.blynchik.magicRangers.util.ValidationUIErrorUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static dev.blynchik.magicRangers.controller.rout.CharacterPageRoutes.*;

@Controller
@RequestMapping(CHARACTER)
@Slf4j
public class CharacterPageController {

    private final CharacterService characterService;
    private final ValidationUIErrorUtil validationUIErrorUtil;

    @Autowired
    public CharacterPageController(CharacterService characterService,
                                   ValidationUIErrorUtil validationUIErrorUtil) {
        this.characterService = characterService;
        this.validationUIErrorUtil = validationUIErrorUtil;
    }

    /**
     * Создает нового персонажа и перенаправляет на страницу с собственным персонажем
     */
    @PostMapping
    public String create(@AuthenticationPrincipal AuthUser authUser,
                         @Valid @ModelAttribute("character") CharacterRequest dto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        log.info("Request POST to {}", CHARACTER);
        if (validationUIErrorUtil.hasValidationErrors(bindingResult)) return CHARACTER + NEW;
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
