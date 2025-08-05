package dev.blynchik.magicRangers.controller;

import dev.blynchik.magicRangers.mapper.AppCharacterMapper;
import dev.blynchik.magicRangers.mapper.AppEventMapper;
import dev.blynchik.magicRangers.model.auth.AuthUser;
import dev.blynchik.magicRangers.model.dto.request.SelectedAppEventOption;
import dev.blynchik.magicRangers.model.dto.response.AppEventResponse;
import dev.blynchik.magicRangers.model.storage.*;
import dev.blynchik.magicRangers.service.mechanic.EventMechanic;
import dev.blynchik.magicRangers.service.mechanic.ResultMechanic;
import dev.blynchik.magicRangers.service.mechanic.RewardMechanic;
import dev.blynchik.magicRangers.service.mechanic.RollMechanic;
import dev.blynchik.magicRangers.service.model.AppCharacterService;
import dev.blynchik.magicRangers.service.model.AppEventService;
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
import static dev.blynchik.magicRangers.model.storage.AppAttributes.valueOf;

@Controller
@RequestMapping(MAIN)
@Slf4j
public class MainPageController {

    private final AppCharacterService characterService;
    private final AppEventService eventService;
    private final AppEventMapper eventMapper;
    private final AppCharacterMapper characterMapper;
    private final ResultMechanic resultMechanic;
    private final RollMechanic rollMechanic;
    private final EventMechanic eventMechanic;
    private final RewardMechanic rewardMechanic;

    @Autowired
    public MainPageController(AppCharacterService characterService,
                              AppEventService eventService,
                              AppEventMapper eventMapper,
                              AppCharacterMapper characterMapper,
                              ResultMechanic resultMechanic,
                              RollMechanic rollMechanic,
                              EventMechanic eventMechanic,
                              RewardMechanic rewardMechanic) {
        this.characterService = characterService;
        this.eventService = eventService;
        this.eventMapper = eventMapper;
        this.characterMapper = characterMapper;
        this.resultMechanic = resultMechanic;
        this.rollMechanic = rollMechanic;
        this.eventMechanic = eventMechanic;
        this.rewardMechanic = rewardMechanic;
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
            AppEventOption eventOption = eventMechanic.getSelectedOptionFromCurrentEvent(
                    valueOf(selectedOption.getAttribute()), selectedOption.getDescr(), options);
            Integer rolledValue = rollMechanic.roll(valueOf(selectedOption.getAttribute()), selectedOption.getAttributeConstraint(), character);
            AppEventOptionResultList optionResultList = resultMechanic.getResultList(eventOption, selectedOption, rolledValue);
            AppProbableResult result = resultMechanic.getResult(optionResultList.getProbableResults());
            log.info("Result defined: {}", result);
            if (eventOption.getRemainingAttempts() <= 0 || result.getIsFinal()) {
                // если нет попыток или результат прекращает попытки, то обнуляем текущий квест и возвращаем результат
                characterService.updateCurrentEvent(character.getId(), null);
            } else {
                // если попытки есть и результат не прекращает попытки, то сохраняем событие с уменьшенным количеством попыток, возвращаем то же самое событие
                characterService.updateCurrentEvent(character.getId(), character.getCurrentEvent());
                model.addAttribute("options", character.getCurrentEvent().getOptions());
            }
            // награждение
            character = rewardMechanic.setReward(character, result.getRewardList());
            model.addAttribute("character", characterMapper.mapToDto(character));
            model.addAttribute("event", eventMapper.mapToDto(character.getCurrentEvent()));
            model.addAttribute("result", eventMapper.mapToDto(character.getCurrentEvent().getTitle(),
                    valueOf(selectedOption.getAttribute()),
                    selectedOption.getDescr(),
                    optionResultList.getMinDifficulty(),
                    rolledValue,
                    selectedOption.getAttributeConstraint(),
                    result));
            return "/main";
        }
        return "redirect:" + MAIN;
    }
}
