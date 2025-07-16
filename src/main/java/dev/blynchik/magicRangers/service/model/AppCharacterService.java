package dev.blynchik.magicRangers.service.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.blynchik.magicRangers.exception.AppException;
import dev.blynchik.magicRangers.model.storage.AppCharacter;
import dev.blynchik.magicRangers.model.storage.AppEvent;
import dev.blynchik.magicRangers.repo.CharacterRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static dev.blynchik.magicRangers.exception.ExceptionMessage.*;

@Service
@Slf4j
@Transactional(readOnly = true)
public class AppCharacterService {

    private final CharacterRepo characterRepo;
    private final ObjectMapper objectMapper;

    @Autowired
    public AppCharacterService(CharacterRepo characterRepo,
                               ObjectMapper objectMapper) {
        this.characterRepo = characterRepo;
        this.objectMapper = objectMapper;
    }

    /**
     * Метод обновляет текущее событие персонажа
     * или выбрасывает исключение, если не смог этого сделать
     */
    @Transactional
    public void updateCurrentEvent(Long characterId, AppEvent event) {
        String eventJson = null;
        try {
            if (event != null) {
                eventJson = objectMapper.writeValueAsString(event);
            }
        } catch (JsonProcessingException e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (characterRepo.updateCurrentEvent(characterId, eventJson) == 0) {
            throw new AppException(UPDATE_DENIED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Создаем персонажа
     */
    @Transactional
    public AppCharacter create(Long appUserId, AppCharacter character) {
        log.info("Create character {} for appUser {}", character, appUserId);
        character.setAppUserId(appUserId);
        return this.save(character);
    }

    /**
     * Ищем персонажа по id,
     * если не находим, то выбрасываем исключение
     */
    public AppCharacter getById(Long characterId) {
        return this.getOptById(characterId)
                .orElseThrow(() -> new AppException(CHARACTER_NOT_FOUND.formatted(characterId), HttpStatus.NOT_FOUND));
    }

    /**
     * Ищем персонажа по id пользователя,
     * если не находим, то выбрасываем исключение
     */
    public AppCharacter getByAppUserId(Long appUserId) {
        return this.getOptByAppUserId(appUserId)
                .orElseThrow(() -> new AppException(APPUSERS_CHARACTER_NOT_FOUND.formatted(appUserId), HttpStatus.NOT_FOUND));
    }

    /**
     * Ищем персонажа по id
     */
    public Optional<AppCharacter> getOptById(Long characterId) {
        log.info("Get character by id {}", characterId);
        return characterRepo.findById(characterId);
    }

    /**
     * Ищем персонажа по id пользователя
     */
    public Optional<AppCharacter> getOptByAppUserId(Long appUserId) {
        log.info("Get character by appUser id {}", appUserId);
        return characterRepo.findByAppUserId(appUserId);
    }

    /**
     * Возвращает, существует ли персонаж у пользователя по его id
     */
    public Boolean existsByAppUserId(Long appUserId) {
        log.info("Get character existence for appUser id {}", appUserId);
        return characterRepo.existsByAppUserId(appUserId);
    }

    /**
     * Возвращает, существует ли у персонажа событие по его id
     */
    public Boolean existsByIdAndCurrentEventIsNotNull(Long characterId) {
        log.info("Get event existence for character id {}", characterId);
        return characterRepo.existsByIdAndCurrentEventIsNotNull(characterId);
    }

    /**
     * Сохраняет персонажа
     */
    @Transactional
    public AppCharacter save(AppCharacter character) {
        character.setName(character.getName().trim());
        character.setUpdatedAt(LocalDateTime.now());
        log.info("Save character {}", character);
        return characterRepo.save(character);
    }
}
