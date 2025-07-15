package dev.blynchik.magicRangers.mapper;

import dev.blynchik.magicRangers.model.dto.request.AppEventOptionRequest;
import dev.blynchik.magicRangers.model.dto.request.AppEventOptionResultListRequest;
import dev.blynchik.magicRangers.model.dto.request.AppEventRequest;
import dev.blynchik.magicRangers.model.dto.response.AppEventOptionResponse;
import dev.blynchik.magicRangers.model.dto.response.AppEventOptionResultListResponse;
import dev.blynchik.magicRangers.model.dto.response.AppEventResponse;
import dev.blynchik.magicRangers.model.storage.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
@Slf4j
public class EventMapper {

    /**
     * Конвертирует dto для создания Event
     */
    public AppEvent mapToEntity(AppEventRequest dto) {
        log.info("Convert {} to {}", dto.getClass().getName(), AppEvent.class.getName());
        return new AppEvent(dto.getTitle(), dto.getDescr(),
                dto.getOptionRequests().stream()
                        .map(this::mapToEntity)
                        .toList(),
                LocalDateTime.now());
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
        return new AppEventOptionResponse(option.getAttribute().name(), option.getDescr());
    }

    /**
     * Получаем объект для ответа на выбор варианта
     */
    public AppEventOptionResultListResponse mapToDto(String eventTitle, String selectedOption, AppEventOptionResultList optionResultSet) {
        log.info("Convert {} to {}", optionResultSet.getClass().getName(), AppEventOptionResultListResponse.class.getName());
        return new AppEventOptionResultListResponse(eventTitle,
                selectedOption,
                optionResultSet.getMinDifficulty(),
                optionResultSet.getProbableResults().stream()
                        .findFirst()
                        .get()
                        .getDescr());
    }

    /**
     * Конвертирует dto для создания EventOption
     */
    public AppEventOption mapToEntity(AppEventOptionRequest dto) {
        log.info("Convert {} to {}", dto.getClass().getName(), AppEventOption.class.getName());
        return new AppEventOption(AppAttributes.valueOf(dto.getAttribute()), dto.getDescr(), dto.getResults().stream()
                .map(this::mapToEntity)
                .toList());
    }

    /**
     * Конвертирует dto для создания EventOptionResult
     */
    public AppEventOptionResultList mapToEntity(AppEventOptionResultListRequest dto) {
        log.info("Convert {} to {}", dto.getClass().getName(), AppEventOptionResultList.class.getName());
        return new AppEventOptionResultList(dto.getMinDifficulty(), dto.getProbableResults().stream()
                .map(r -> new AppProbableResult(r.getProbabilityPercent(), r.getDescr()))
                .toList());
    }
}
