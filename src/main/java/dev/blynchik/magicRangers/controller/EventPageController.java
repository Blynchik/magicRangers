package dev.blynchik.magicRangers.controller;

import dev.blynchik.magicRangers.model.dto.EventResponse;
import dev.blynchik.magicRangers.service.model.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static dev.blynchik.magicRangers.controller.rout.EventPageRoutes.EVENT;

@Controller
@RequestMapping(EVENT)
@Slf4j
public class EventPageController {

    private final EventService eventService;

    @Autowired
    public EventPageController(EventService eventService) {
        this.eventService = eventService;
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
