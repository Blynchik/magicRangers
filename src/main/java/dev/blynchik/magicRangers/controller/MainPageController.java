package dev.blynchik.magicRangers.controller;

import dev.blynchik.magicRangers.exception.AppException;
import dev.blynchik.magicRangers.exception.ExceptionMessage;
import dev.blynchik.magicRangers.model.auth.AuthUser;
import dev.blynchik.magicRangers.model.dto.EventResponse;
import dev.blynchik.magicRangers.model.dto.SelectedEventOption;
import dev.blynchik.magicRangers.model.storage.Character;
import dev.blynchik.magicRangers.model.storage.Event;
import dev.blynchik.magicRangers.model.storage.EventOption;
import dev.blynchik.magicRangers.service.model.CharacterService;
import dev.blynchik.magicRangers.service.model.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

import static dev.blynchik.magicRangers.controller.rout.MainPageRoutes.MAIN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping(MAIN)
@Slf4j
public class MainPageController {

    private final CharacterService characterService;
    private final EventService eventService;

    @Autowired
    public MainPageController(CharacterService characterService,
                              EventService eventService) {
        this.characterService = characterService;
        this.eventService = eventService;
    }

    /**
     * Возвращает главную страницу с событием и персонажем
     */
    @GetMapping
    public String get(@AuthenticationPrincipal AuthUser authUser,
                      Model model) {
        log.info("Request GET to {} by {}", MAIN, authUser.getAppUser().getId());
        Event event;
        Character character = characterService.getByAppUserId(authUser.getAppUser().getId());
        if (characterService.hasEvent(character.getId())) {
            event = character.getCurrentEvent();
            EventResponse eventResponse = eventService.getDto(event);
            model.addAttribute("character", characterService.getDto(character));
            model.addAttribute("event", eventService.getDto(event));
            model.addAttribute("options", eventResponse.getOptions());
            return MAIN;
        }
        event = eventService.getRandom();
        character.setCurrentEvent(event);
        character = characterService.save(character);
        EventResponse eventResponse = eventService.getDto(event);
        model.addAttribute("character", characterService.getDto(character));
        model.addAttribute("event", eventService.getDto(event));
        model.addAttribute("options", eventResponse.getOptions());
        return "/main";
    }

    /**
     * Производит расчеты связанные с выбором варианта решения события и
     * возвращает результат, если такое решение есть у события, которое
     * является текущим у персонажа
     */
    @PostMapping
    public String select(@AuthenticationPrincipal AuthUser authUser,
                         @ModelAttribute SelectedEventOption selectedOption,
                         Model model) {
        log.info("Request POST to {} by {}", MAIN, authUser.getAppUser().getId());
        Character character = characterService.getByAppUserId(authUser.getAppUser().getId());
        if (characterService.hasEvent(character.getId())) {
            Set<EventOption> options = character.getCurrentEvent().getOptions();
            EventOption eventOption = options.stream()
                    .filter(o -> o.getDescr().equals(selectedOption.getDescr()) && o.getAttribute().name().equals(selectedOption.getAttribute()))
                    .findFirst()
                    .orElseThrow(() -> new AppException(ExceptionMessage.NOT_FOUND.formatted(selectedOption.getDescr() + " " + selectedOption.getAttribute()), NOT_FOUND));
            model.addAttribute("character", characterService.getDto(character));
            model.addAttribute("event", eventService.getDto(character.getCurrentEvent()));
            model.addAttribute("result", eventOption.getResults().get(0));
            character.setCurrentEvent(null);
            characterService.save(character);
            return MAIN;
        }
        return "redirect:" + MAIN;
    }
}
