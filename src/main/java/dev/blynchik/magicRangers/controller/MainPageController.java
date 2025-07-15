package dev.blynchik.magicRangers.controller;

import dev.blynchik.magicRangers.exception.AppException;
import dev.blynchik.magicRangers.exception.ExceptionMessage;
import dev.blynchik.magicRangers.mapper.CharacterMapper;
import dev.blynchik.magicRangers.mapper.EventMapper;
import dev.blynchik.magicRangers.model.auth.AuthUser;
import dev.blynchik.magicRangers.model.dto.request.SelectedAppEventOption;
import dev.blynchik.magicRangers.model.dto.response.AppEventResponse;
import dev.blynchik.magicRangers.model.storage.AppCharacter;
import dev.blynchik.magicRangers.model.storage.AppEvent;
import dev.blynchik.magicRangers.model.storage.AppEventOption;
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

import java.util.List;

import static dev.blynchik.magicRangers.controller.rout.MainPageRoutes.MAIN;
import static dev.blynchik.magicRangers.controller.rout.MainPageRoutes.SEARCH;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping(MAIN)
@Slf4j
public class MainPageController {

    private final CharacterService characterService;
    private final EventService eventService;
    private final EventMapper eventMapper;
    private final CharacterMapper characterMapper;

    @Autowired
    public MainPageController(CharacterService characterService,
                              EventService eventService,
                              EventMapper eventMapper,
                              CharacterMapper characterMapper) {
        this.characterService = characterService;
        this.eventService = eventService;
        this.eventMapper = eventMapper;
        this.characterMapper = characterMapper;
    }

    /**
     * Возвращает главную страницу с событием (если он есть) и персонажем
     */
    @GetMapping
    public String get(@AuthenticationPrincipal AuthUser authUser,
                      Model model) {
        log.info("Request GET to {} by {}", MAIN, authUser.getAppUser().getId());
        AppEvent event;
        AppCharacter character = characterService.getByAppUserId(authUser.getAppUser().getId());
        if (characterService.existsByIdAndCurrentEventIsNotNull(character.getId())) {
            event = character.getCurrentEvent();
            AppEventResponse eventResponse = eventMapper.mapToDto(event);
            model.addAttribute("event", eventResponse);
            model.addAttribute("options", eventResponse.getOptions());
        }
        model.addAttribute("character", characterMapper.mapToDto(character));
        return "/main";
    }

    /**
     * После нажатия 'Поиска события' возвращает главную страницу
     * со случайным событием (либо с текущим персонажа) и персонажем
     */
    @PostMapping(SEARCH)
    public String findRandomEvent(@AuthenticationPrincipal AuthUser authUser,
                                  Model model) {
        log.info("Request GET to {} by {}", MAIN + SEARCH, authUser.getAppUser().getId());
        AppCharacter character = characterService.getByAppUserId(authUser.getAppUser().getId());
        AppEvent event;
        if (characterService.existsByIdAndCurrentEventIsNotNull(character.getId())) {
            event = character.getCurrentEvent();
        } else {
            event = eventService.getRandom();
            characterService.updateCurrentEvent(character.getId(), event);
        }
        AppEventResponse eventResponse = eventMapper.mapToDto(event);
        model.addAttribute("character", characterMapper.mapToDto(character));
        model.addAttribute("event", eventResponse);
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
                         @ModelAttribute SelectedAppEventOption selectedOption,
                         Model model) {
        log.info("Request POST to {} by {}", MAIN, authUser.getAppUser().getId());
        AppCharacter character = characterService.getByAppUserId(authUser.getAppUser().getId());
        if (characterService.existsByIdAndCurrentEventIsNotNull(character.getId())) {
            List<AppEventOption> options = character.getCurrentEvent().getOptions();
            AppEventOption eventOption = options.stream()
                    .filter(o -> o.getDescr().equals(selectedOption.getDescr()) && o.getAttribute().name().equals(selectedOption.getAttribute()))
                    .findFirst()
                    .orElseThrow(() -> new AppException(ExceptionMessage.NOT_FOUND.formatted(selectedOption.getDescr() + " " + selectedOption.getAttribute()), NOT_FOUND));
            model.addAttribute("character", characterMapper.mapToDto(character));
            model.addAttribute("event", eventMapper.mapToDto(character.getCurrentEvent()));
            model.addAttribute("result", eventMapper.mapToDto(character.getCurrentEvent().getTitle(),
                    selectedOption.getDescr(),
                    eventOption.getResults().get(0)));
            characterService.updateCurrentEvent(character.getId(), null);
            return "/main";
        }
        return "redirect:" + MAIN;
    }
}
