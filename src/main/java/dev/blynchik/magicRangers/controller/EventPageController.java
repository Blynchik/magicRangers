package dev.blynchik.magicRangers.controller;

import dev.blynchik.magicRangers.mapper.AppEventMapper;
import dev.blynchik.magicRangers.model.auth.AuthUser;
import dev.blynchik.magicRangers.model.dto.request.AppEventRequest;
import dev.blynchik.magicRangers.model.dto.response.AppEventResponse;
import dev.blynchik.magicRangers.service.model.AppEventService;
import dev.blynchik.magicRangers.util.ValidationUIErrorUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.util.UriEncoder;

import static dev.blynchik.magicRangers.controller.rout.CharacterPageRoutes.NEW;
import static dev.blynchik.magicRangers.controller.rout.EventPageRoutes.EVENT;

@Controller
@RequestMapping(EVENT)
@Slf4j
public class EventPageController {

    private final AppEventService eventService;
    private final ValidationUIErrorUtil validationUIErrorUtil;
    private final AppEventMapper eventMapper;

    @Autowired
    public EventPageController(AppEventService eventService,
                               ValidationUIErrorUtil validationUIErrorUtil,
                               AppEventMapper eventMapper) {
        this.eventService = eventService;
        this.validationUIErrorUtil = validationUIErrorUtil;
        this.eventMapper = eventMapper;
    }

    /**
     * Создает новое событие и перенаправляет на страницу с отображения события
     */
    @PostMapping
    public String create(@AuthenticationPrincipal AuthUser authUser,
                         @Valid @ModelAttribute("event") AppEventRequest dto,
                         BindingResult bindingResult) {
        log.info("Request POST to {} by {}", EVENT, authUser.getId());
        if (validationUIErrorUtil.hasValidationErrors(bindingResult)) return EVENT + NEW;
        AppEventResponse event = eventMapper.mapToDto(
                eventService.create(
                        eventMapper.mapToEntity(dto)));
        return "redirect:" + EVENT + "?name=" + UriEncoder.encode(event.getTitle());
    }

    /**
     * Возвращает страницу создания персонажа
     */
    @GetMapping(NEW)
    public String showCreate(@AuthenticationPrincipal AuthUser authUser,
                             Model model) {
        log.info("Request GET to {}", EVENT + NEW);
        model.addAttribute("event", new AppEventRequest());
        model.addAttribute("authUser", authUser);
        return "event/new";
    }

    /**
     * По названию получаем событие
     */
    @GetMapping
    public String get(@AuthenticationPrincipal AuthUser authUser,
            @RequestParam(name = "name", defaultValue = "Конкурс талантов", required = false)
            String name,
            Model model) {
        log.info("Request GET to {} with name {}", EVENT, name);
        AppEventResponse event = eventMapper.mapToDto(eventService.getByTitle(name));
        model.addAttribute("event", event);
        model.addAttribute("authUser", authUser);
        return "event/view";
    }
}
