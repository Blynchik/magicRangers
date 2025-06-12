package dev.blynchik.magicRangers.mapper;

import dev.blynchik.magicRangers.model.dto.EventResponse;
import dev.blynchik.magicRangers.model.storage.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventMapper {

    /**
     * Конвертирует Event в dto для ответа
     */
    public EventResponse mapToDto(Event event) {
        log.info("Convert {} to {}", event.getClass().getName(), EventResponse.class.getName());
        return new EventResponse(event.getTitle(), event.getDescr());
    }
}
