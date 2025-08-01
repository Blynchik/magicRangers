package dev.blynchik.magicRangers.service.model;

import dev.blynchik.magicRangers.exception.AppException;
import dev.blynchik.magicRangers.model.storage.AppEvent;
import dev.blynchik.magicRangers.repo.AppEventRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static dev.blynchik.magicRangers.exception.ExceptionMessage.NOT_FOUND;

@Service
@Slf4j
@Transactional(readOnly = true)
public class AppEventService {

    private final AppEventRepo eventRepo;

    @Autowired
    public AppEventService(AppEventRepo eventRepo) {
        this.eventRepo = eventRepo;
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
    public AppEvent create(AppEvent event) {
        log.info("Create event {}", event);
        return this.save(event);
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
        event.setUpdatedAt(LocalDateTime.now());
        log.info("Save event {}", event);
        return eventRepo.save(event);
    }
}
