package dev.blynchik.magicRangers.service.model;

import dev.blynchik.magicRangers.exception.AppException;
import dev.blynchik.magicRangers.mapper.CharacterMapper;
import dev.blynchik.magicRangers.model.dto.CharacterRequest;
import dev.blynchik.magicRangers.model.storage.Character;
import dev.blynchik.magicRangers.repo.CharacterRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static dev.blynchik.magicRangers.exception.ExceptionMessage.APPUSERS_CHARACTER_NOT_FOUND;
import static dev.blynchik.magicRangers.exception.ExceptionMessage.CHARACTER_NOT_FOUND;

@Service
@Slf4j
@Transactional(readOnly = true)
public class CharacterService {

    private final CharacterRepo characterRepo;
    private final CharacterMapper characterMapper;

    @Autowired
    public CharacterService(CharacterRepo characterRepo,
                            CharacterMapper characterMapper) {
        this.characterRepo = characterRepo;
        this.characterMapper = characterMapper;
    }

    /**
     * Создаем персонажа
     */
    @Transactional
    public Character create(Long appUserId, CharacterRequest dto) {
        log.info("Create character {} for appUser {}", dto, appUserId);
        return characterRepo.save(
                characterMapper.mapToEntity(appUserId, dto));
    }

    /**
     * Ищем персонажа по id,
     * если не находим, то выбрасываем исключение
     */
    public Character getById(Long characterId) {
        return this.getOptById(characterId)
                .orElseThrow(() -> new AppException(CHARACTER_NOT_FOUND.formatted(characterId), HttpStatus.NOT_FOUND));
    }

    /**
     * Ищем персонажа по id пользователя,
     * если не находим, то выбрасываем исключение
     */
    public Character getByAppUserId(Long appUserId) {
        return this.getOptByAppUserId(appUserId)
                .orElseThrow(() -> new AppException(APPUSERS_CHARACTER_NOT_FOUND.formatted(appUserId), HttpStatus.NOT_FOUND));
    }

    /**
     * Ищем персонажа по id
     */
    private Optional<Character> getOptById(Long characterId) {
        log.info("Get character by id {}", characterId);
        return characterRepo.findById(characterId);
    }

    /**
     * Ищем персонажа по id пользователя
     */
    private Optional<Character> getOptByAppUserId(Long appUserId) {
        log.info("Get character by appUser id {}", appUserId);
        return characterRepo.findByAppUserId(appUserId);
    }
}
