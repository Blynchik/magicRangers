package dev.blynchik.magicRangers.mapper;

import dev.blynchik.magicRangers.model.dto.request.AppEventOptionRequest;
import dev.blynchik.magicRangers.model.dto.request.AppEventOptionResultListRequest;
import dev.blynchik.magicRangers.model.dto.request.AppEventRequest;
import dev.blynchik.magicRangers.model.dto.response.AppEventOptionResponse;
import dev.blynchik.magicRangers.model.dto.response.AppEventOptionResultResponse;
import dev.blynchik.magicRangers.model.dto.response.AppEventResponse;
import dev.blynchik.magicRangers.model.storage.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@Slf4j
public class AppEventMapper {

    /**
     * Конвертирует dto для создания Event
     */
    public AppEvent mapToEntity(AppEventRequest dto) {
        log.info("Convert {} to {}", dto.getClass().getName(), AppEvent.class.getName());
        return new AppEvent(dto.getTitle(), dto.getDescr(),
                dto.getOptionRequests().stream()
                        .map(this::mapToEntity)
                        .toList());
    }

    /**
     * Конвертирует Event в dto для ответа
     */
    public AppEventResponse mapToDto(AppEvent event) {
        log.info("Convert {} to {}", event.getClass().getName(), AppEventResponse.class.getName());
        return new AppEventResponse(event.getTitle(), event.getDescr(),
                event.getOptions().stream()
                        .map(this::mapToDto)
                        .collect(Collectors.toSet()));
    }

    /**
     * Конвертирует EventOption в dto для ответа
     */
    public AppEventOptionResponse mapToDto(AppEventOption option) {
        log.info("Convert {} to {}", option.getClass().getName(), AppEventOptionResponse.class.getName());
        return new AppEventOptionResponse(option.getAttribute().name(), option.getDescr(), option.getRemainingAttempts());
    }

    /**
     * Получаем объект для ответа на выбор варианта
     */
    public AppEventOptionResultResponse mapToDto(String eventTitle, AppAttributes attribute, String selectedOption, Integer minDifficulty, Integer rolledValue, Integer constraint, AppProbableResult result) {
        log.info("Convert {} to {}", result.getClass().getName(), AppEventOptionResultResponse.class.getName());
        return new AppEventOptionResultResponse(eventTitle,
                attribute.name(),
                selectedOption,
                minDifficulty,
                rolledValue,
                constraint,
                result.getDescr());
    }

    /**
     * Конвертирует dto для создания EventOption
     */
    public AppEventOption mapToEntity(AppEventOptionRequest dto) {
        log.info("Convert {} to {}", dto.getClass().getName(), AppEventOption.class.getName());
        return new AppEventOption(AppAttributes.valueOf(dto.getAttribute()), dto.getDescr(), dto.getRemainingAttempts(), dto.getResults().stream()
                .map(this::mapToEntity)
                .toList());
    }

    /**
     * Конвертирует dto для создания EventOptionResult
     */
    public AppEventOptionResultList mapToEntity(AppEventOptionResultListRequest dto) {
        log.info("Convert {} to {}", dto.getClass().getName(), AppEventOptionResultList.class.getName());
        return new AppEventOptionResultList(dto.getMinDifficulty(), dto.getProbableResults().stream()
                .map(r -> new AppProbableResult(r.getProbabilityPercent(), r.getDescr(), r.isFinal()))
                .toList());
    }
}
