package dev.blynchik.magicRangers.mapper;

import dev.blynchik.magicRangers.model.dto.EventRequest;
import dev.blynchik.magicRangers.model.dto.EventResponse;
import dev.blynchik.magicRangers.model.storage.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class EventMapper {

    /**
     * Конвертирует dto для создания Event
     */
    public Event mapToEntity(EventRequest dto) {
        log.info("Convert {} to {}", dto.getClass().getName(), Event.class.getName());
        return new Event(dto.getTitle(), dto.getDescr(), LocalDateTime.now());
    }

    /**
     * Конвертирует Event в dto для ответа
     */
    public EventResponse mapToDto(Event event) {
        log.info("Convert {} to {}", event.getClass().getName(), EventResponse.class.getName());
        return new EventResponse(event.getTitle(), event.getDescr());
    }
}
