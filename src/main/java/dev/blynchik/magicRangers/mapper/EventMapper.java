package dev.blynchik.magicRangers.mapper;

import dev.blynchik.magicRangers.model.dto.*;
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
    public Event mapToEntity(EventRequest dto) {
        log.info("Convert {} to {}", dto.getClass().getName(), Event.class.getName());
        return new Event(dto.getTitle(), dto.getDescr(),
                dto.getOptionRequests().stream()
                        .map(this::mapToEntity)
                        .collect(Collectors.toSet()),
                LocalDateTime.now());
    }

    /**
     * Конвертирует Event в dto для ответа
     */
    public EventResponse mapToDto(Event event) {
        log.info("Convert {} to {}", event.getClass().getName(), EventResponse.class.getName());
        return new EventResponse(event.getTitle(), event.getDescr(),
                event.getOptions().stream()
                        .map(this::mapToDto)
                        .collect(Collectors.toSet()));
    }

    /**
     * Конвертирует EventOption в dto для ответа
     */
    public EventOptionResponse mapToDto(EventOption option) {
        log.info("Convert {} to {}", option.getClass().getName(), EventOptionResponse.class.getName());
        return new EventOptionResponse(option.getAttribute().name(), option.getDescr());
    }

    /**
     * Получаем объект для ответа на выбор варианта
     */
    public EventOptionResultSetResponse mapToDto(String eventTitle, String selectedOption, EventOptionResultSet optionResultSet) {
        log.info("Convert {} to {}", optionResultSet.getClass().getName(), EventOptionResultSetResponse.class.getName());
        return new EventOptionResultSetResponse(eventTitle,
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
    public EventOption mapToEntity(EventOptionRequest dto) {
        log.info("Convert {} to {}", dto.getClass().getName(), EventOption.class.getName());
        return new EventOption(Attributes.valueOf(dto.getAttribute()), dto.getDescr(), dto.getResults().stream()
                .map(this::mapToEntity)
                .toList());
    }

    /**
     * Конвертирует dto для создания EventOptionResult
     */
    public EventOptionResultSet mapToEntity(EventOptionResultSetRequest dto) {
        log.info("Convert {} to {}", dto.getClass().getName(), EventOptionResultSet.class.getName());
        return new EventOptionResultSet(dto.getMinDifficulty(), dto.getProbableResults().stream()
                .map(r -> new ProbableResult(r.getProbabilityPercent(), r.getDescr()))
                .collect(Collectors.toSet()));
    }
}
