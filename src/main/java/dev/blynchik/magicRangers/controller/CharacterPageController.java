package dev.blynchik.magicRangers.controller;

import dev.blynchik.magicRangers.mapper.AppCharacterMapper;
import dev.blynchik.magicRangers.model.auth.AuthUser;
import dev.blynchik.magicRangers.model.dto.request.AppCharacterRequest;
import dev.blynchik.magicRangers.model.dto.response.AppCharacterResponse;
import dev.blynchik.magicRangers.service.model.AppCharacterService;
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
import static dev.blynchik.magicRangers.controller.rout.MainPageRoutes.MAIN;

@Controller
@RequestMapping(CHARACTER)
@Slf4j
public class CharacterPageController {

    private final AppCharacterService characterService;
    private final ValidationUIErrorUtil validationUIErrorUtil;
    private final AppCharacterMapper characterMapper;

    @Autowired
    public CharacterPageController(AppCharacterService characterService,
                                   ValidationUIErrorUtil validationUIErrorUtil,
                                   AppCharacterMapper characterMapper) {
        this.characterService = characterService;
        this.validationUIErrorUtil = validationUIErrorUtil;
        this.characterMapper = characterMapper;
    }

    /**
     * Создает нового персонажа и перенаправляет на страницу с собственным персонажем
     */
    @PostMapping
    public String create(@AuthenticationPrincipal AuthUser authUser,
                         @Valid @ModelAttribute("character") AppCharacterRequest dto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        log.info("Request POST to {} by {}", CHARACTER, authUser.getAppUser().getId());
        if (characterService.existsByAppUserId(authUser.getAppUser().getId())) {
            bindingResult.reject("character.constraint.message.appUserHasCharacter");
        }
        if (validationUIErrorUtil.hasValidationErrors(bindingResult)) return CHARACTER + NEW;
        AppCharacterResponse ch = characterMapper.mapToDto(
                characterService.create(
                        authUser.getAppUser().getId(), characterMapper.mapToEntity(authUser.getAppUser().getId(), dto)));
        redirectAttributes.addFlashAttribute("character", ch);
        return "redirect:" + MAIN;
    }

    /**
     * Возвращает страницу создания персонажа
     */
    @GetMapping(NEW)
    public String showCreate(Model model) {
        log.info("Request GET to {}", CHARACTER + NEW);
        model.addAttribute("character", new AppCharacterRequest());
        return "character/new";
    }

    /**
     * Возвращает страницу персонажа по id персонажа
     */
    @GetMapping(ID)
    public String get(@PathVariable Long id,
                      Model model) {
        log.info("Request GET to {}", CHARACTER + ID);
        AppCharacterResponse ch = characterMapper.mapToDto(
                characterService.getById(id));
        model.addAttribute("character", ch);
        return "character/view";
    }

    /**
     * Возвращает страницу с собственным персонажем
     */
    @GetMapping(MY)
    public String getMy(@AuthenticationPrincipal AuthUser authUser,
                        Model model) {
        log.info("Request GET to {} by {}", CHARACTER + MY, authUser.getAppUser().getId());
        AppCharacterResponse ch = characterMapper.mapToDto(
                characterService.getByAppUserId(authUser.getAppUser().getId()));
        model.addAttribute("character", ch);
        return "character/view";
    }
}
