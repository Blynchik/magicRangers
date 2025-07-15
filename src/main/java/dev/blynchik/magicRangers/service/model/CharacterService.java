package dev.blynchik.magicRangers.service.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.blynchik.magicRangers.exception.AppException;
import dev.blynchik.magicRangers.mapper.CharacterMapper;
import dev.blynchik.magicRangers.model.dto.request.AppCharacterRequest;
import dev.blynchik.magicRangers.model.dto.response.AppCharacterResponse;
import dev.blynchik.magicRangers.model.storage.AppCharacter;
import dev.blynchik.magicRangers.model.storage.AppEvent;
import dev.blynchik.magicRangers.repo.CharacterRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static dev.blynchik.magicRangers.exception.ExceptionMessage.*;

@Service
@Slf4j
@Transactional(readOnly = true)
public class CharacterService {

    private final CharacterRepo characterRepo;
    private final CharacterMapper characterMapper;
    private final ObjectMapper objectMapper;

    @Autowired
    public CharacterService(CharacterRepo characterRepo,
                            CharacterMapper characterMapper) {
        this.characterRepo = characterRepo;
        this.characterMapper = characterMapper;
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
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
     * Создаем персонажа, возвращая dto для ответа
     */
    @Transactional
    public AppCharacterResponse createWithDto(Long appUserId, AppCharacterRequest dto) {
        return characterMapper.mapToDto(this.create(appUserId, dto));
    }

    /**
     * Создаем персонажа
     */
    @Transactional
    public AppCharacter create(Long appUserId, AppCharacterRequest dto) {
        log.info("Create character {} for appUser {}", dto, appUserId);
        return this.save(characterMapper.mapToEntity(appUserId, dto));
    }

    /**
     * Возвращаем dto персонажа для ответа по id персонажа
     */
    public AppCharacterResponse getDtoById(Long characterId) {
        return characterMapper.mapToDto(this.getById(characterId));
    }

    /**
     * Возвращаем dto персонажа для ответа по id пользователя
     */
    public AppCharacterResponse getDtoByAppUserId(Long appUserId) {
        return characterMapper.mapToDto(this.getByAppUserId(appUserId));
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
     * Возвращаем dto персонажа для ответа
     */
    public AppCharacterResponse getDto(AppCharacter character) {
        return characterMapper.mapToDto(character);
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
        log.info("Save character {}", character);
        return characterRepo.save(character);
    }
}
