package dev.blynchik.magicRangers.controller;

import dev.blynchik.magicRangers.model.auth.AuthUser;
import dev.blynchik.magicRangers.model.dto.EventRequest;
import dev.blynchik.magicRangers.model.dto.EventResponse;
import dev.blynchik.magicRangers.service.model.EventService;
import dev.blynchik.magicRangers.util.ValidationUIErrorUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static dev.blynchik.magicRangers.controller.rout.CharacterPageRoutes.CHARACTER;
import static dev.blynchik.magicRangers.controller.rout.CharacterPageRoutes.NEW;
import static dev.blynchik.magicRangers.controller.rout.EventPageRoutes.EVENT;

@Controller
@RequestMapping(EVENT)
@Slf4j
public class EventPageController {

    private final EventService eventService;
    private final ValidationUIErrorUtil validationUIErrorUtil;

    @Autowired
    public EventPageController(EventService eventService,
                               ValidationUIErrorUtil validationUIErrorUtil) {
        this.eventService = eventService;
        this.validationUIErrorUtil = validationUIErrorUtil;
    }

    /**
     * Создает новое событие и перенаправляет на страницу с отображения события
     */
    @PostMapping
    public String create(@AuthenticationPrincipal AuthUser authUser,
                         @Valid @ModelAttribute("event") EventRequest dto,
                         BindingResult bindingResult) {
        log.info("Request POST to {} by {}", EVENT, authUser.getAppUser().getId());
        if (validationUIErrorUtil.hasValidationErrors(bindingResult)) return EVENT + NEW;
        EventResponse event = eventService.createWithDto(dto);
        return "redirect:" + EVENT + "?name=" + event.getTitle();
    }

    /**
     * Возвращает страницу создания персонажа
     */
    @GetMapping(NEW)
    public String showCreate(Model model) {
        log.info("Request GET to {}", EVENT + NEW);
        model.addAttribute("event", new EventRequest());
        return "event/new";
    }

    /**
     * По названию получаем событие
     */
    @GetMapping
    public String get(@RequestParam(name = "name", defaultValue = "Конкурс талантов", required = false)
                      String name,
                      Model model) {
        log.info("Request GET to {} with name {}", EVENT, name);
        EventResponse event = eventService.getDtoByTitle(name);
        model.addAttribute("event", event);
        return "event/view";
    }
}
