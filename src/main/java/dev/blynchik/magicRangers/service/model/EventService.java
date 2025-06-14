package dev.blynchik.magicRangers.service.model;

import dev.blynchik.magicRangers.exception.AppException;
import dev.blynchik.magicRangers.mapper.EventMapper;
import dev.blynchik.magicRangers.model.dto.EventRequest;
import dev.blynchik.magicRangers.model.dto.EventResponse;
import dev.blynchik.magicRangers.model.storage.Event;
import dev.blynchik.magicRangers.repo.EventRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static dev.blynchik.magicRangers.exception.ExceptionMessage.ACTION_NOT_FOUND;

@Service
@Slf4j
@Transactional(readOnly = true)
public class EventService {

    private final EventRepo eventRepo;
    private final EventMapper eventMapper;

    @Autowired
    public EventService(EventRepo eventRepo,
                        EventMapper eventMapper) {
        this.eventRepo = eventRepo;
        this.eventMapper = eventMapper;
    }

    /**
     * Создаем событие, возвращая dto для ответа
     */
    @Transactional
    public EventResponse createWithDto(EventRequest dto) {
        return eventMapper.mapToDto(this.create(dto));
    }

    /**
     * Создаем событие
     */
    @Transactional
    public Event create(EventRequest dto) {
        log.info("Create event {}", dto);
        return this.save(eventMapper.mapToEntity(dto));
    }

    /**
     * Возвращаем dto события для ответа по id
     */
    public EventResponse getDtoById(Long id) {
        return eventMapper.mapToDto(this.getById(id));
    }

    /**
     * Возвращаем dto события для ответа по названию
     */
    public EventResponse getDtoByTitle(String title) {
        return eventMapper.mapToDto(this.getByTitle(title));
    }

    /**
     * Ищем событие по названию,
     * если не находим, то выбрасываем исключение
     */
    public Event getByTitle(String title) {
        return this.getOptByTitle(title)
                .orElseThrow(() -> new AppException(ACTION_NOT_FOUND.formatted(title), HttpStatus.NOT_FOUND));
    }

    /**
     * Ищем событие по id,
     * если не находим, то выбрасываем исключение
     */
    public Event getById(Long id) {
        return this.getOptById(id)
                .orElseThrow(() -> new AppException(ACTION_NOT_FOUND.formatted(id), HttpStatus.NOT_FOUND));
    }

    /**
     * Ищем событие по id
     */
    public Optional<Event> getOptById(Long id) {
        log.info("Get event by id {}", id);
        return eventRepo.findById(id);
    }

    /**
     * Ищем событие по названию
     */
    public Optional<Event> getOptByTitle(String title) {
        log.info("Get event by title {}", title);
        return eventRepo.findByTitle(title);
    }

    // @Transactional в private и во внутри не работает
    private Event save(Event event) {
        event.setTitle(event.getTitle().trim());
        event.setDescr(event.getDescr().trim());
        log.info("Save event {}", event);
        return eventRepo.save(event);
    }
}
