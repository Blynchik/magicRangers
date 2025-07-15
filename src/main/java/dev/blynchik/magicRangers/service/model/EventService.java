package dev.blynchik.magicRangers.service.model;

import dev.blynchik.magicRangers.exception.AppException;
import dev.blynchik.magicRangers.mapper.EventMapper;
import dev.blynchik.magicRangers.model.dto.request.AppEventRequest;
import dev.blynchik.magicRangers.model.dto.response.AppEventResponse;
import dev.blynchik.magicRangers.model.storage.AppEvent;
import dev.blynchik.magicRangers.repo.EventRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static dev.blynchik.magicRangers.exception.ExceptionMessage.NOT_FOUND;

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
    public AppEventResponse createWithDto(AppEventRequest dto) {
        return eventMapper.mapToDto(this.create(dto));
    }

    /**
     * Проверяем наличие события с таким же названием
     */
    public Boolean existsByTitle(String title) {
        log.info("Check event existence by title: {}", title);
        return eventRepo.existsByTitle(title);
    }

    /**
     * Создаем событие
     */
    @Transactional
    public AppEvent create(AppEventRequest dto) {
        log.info("Create event {}", dto);
        return this.save(eventMapper.mapToEntity(dto));
    }

    /**
     * Возвращаем dto события для ответа по id
     */
    public AppEventResponse getDtoById(Long id) {
        return eventMapper.mapToDto(this.getById(id));
    }

    /**
     * Возвращаем dto события для ответа по названию
     */
    public AppEventResponse getDtoByTitle(String title) {
        return eventMapper.mapToDto(this.getByTitle(title));
    }

    /**
     * Возвращаем dto случайного события для ответа
     */
    public AppEventResponse getDtoRandom() {
        return eventMapper.mapToDto(this.getRandom());
    }

    /**
     * Возвращаем dto события для ответа
     */
    public AppEventResponse getDto(AppEvent event) {
        return eventMapper.mapToDto(event);
    }

    /**
     * Ищем событие по названию,
     * если не находим, то выбрасываем исключение
     */
    public AppEvent getByTitle(String title) {
        return this.getOptByTitle(title)
                .orElseThrow(() -> new AppException(NOT_FOUND.formatted(title), HttpStatus.NOT_FOUND));
    }

    /**
     * Ищем событие по id,
     * если не находим, то выбрасываем исключение
     */
    public AppEvent getById(Long id) {
        return this.getOptById(id)
                .orElseThrow(() -> new AppException(NOT_FOUND.formatted(id), HttpStatus.NOT_FOUND));
    }

    /**
     * Ищем случайное событие,
     * если не находим, то выбрасываем исключение
     */
    public AppEvent getRandom() {
        return this.getOptRandom()
                .orElseThrow(() -> new AppException(NOT_FOUND.formatted("Random"), HttpStatus.NOT_FOUND));
    }

    /**
     * Ищем событие по id
     */
    public Optional<AppEvent> getOptById(Long id) {
        log.info("Get event by id {}", id);
        return eventRepo.findById(id);
    }

    /**
     * Ищем событие по названию
     */
    public Optional<AppEvent> getOptByTitle(String title) {
        log.info("Get event by title {}", title);
        return eventRepo.findByTitle(title);
    }

    /**
     * Ищем случайное событие
     */
    public Optional<AppEvent> getOptRandom() {
        log.info("Get random event");
        return eventRepo.findRandom();
    }

    @Transactional
    public AppEvent save(AppEvent event) {
        event.setTitle(event.getTitle().trim());
        event.setDescr(event.getDescr().trim());
        log.info("Save event {}", event);
        return eventRepo.save(event);
    }
}
