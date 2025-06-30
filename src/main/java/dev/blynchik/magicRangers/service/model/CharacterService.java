package dev.blynchik.magicRangers.service.model;

import dev.blynchik.magicRangers.exception.AppException;
import dev.blynchik.magicRangers.mapper.CharacterMapper;
import dev.blynchik.magicRangers.model.dto.CharacterRequest;
import dev.blynchik.magicRangers.model.dto.CharacterResponse;
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
     * Создаем персонажа, возвращая dto для ответа
     */
    @Transactional
    public CharacterResponse createWithDto(Long appUserId, CharacterRequest dto) {
        return characterMapper.mapToDto(this.create(appUserId, dto));
    }

    /**
     * Создаем персонажа
     */
    @Transactional
    public Character create(Long appUserId, CharacterRequest dto) {
        log.info("Create character {} for appUser {}", dto, appUserId);
        return this.save(characterMapper.mapToEntity(appUserId, dto));
    }

    /**
     * Возвращаем dto персонажа для ответа по id персонажа
     */
    public CharacterResponse getDtoById(Long characterId) {
        return characterMapper.mapToDto(this.getById(characterId));
    }

    /**
     * Возвращаем dto персонажа для ответа по id пользователя
     */
    public CharacterResponse getDtoByAppUserId(Long appUserId) {
        return characterMapper.mapToDto(this.getByAppUserId(appUserId));
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
     * Возвращаем dto персонажа для ответа
     */
    public CharacterResponse getDto(Character character) {
        return characterMapper.mapToDto(character);
    }

    /**
     * Ищем персонажа по id
     */
    public Optional<Character> getOptById(Long characterId) {
        log.info("Get character by id {}", characterId);
        return characterRepo.findById(characterId);
    }

    /**
     * Ищем персонажа по id пользователя
     */
    public Optional<Character> getOptByAppUserId(Long appUserId) {
        log.info("Get character by appUser id {}", appUserId);
        return characterRepo.findByAppUserId(appUserId);
    }

    /**
     * Возвращает, существует ли персонаж у пользователя по его id
     */
    public Boolean hasCharacter(Long appUserId) {
        log.info("Get character existence for appUser id {}", appUserId);
        return characterRepo.existsByAppUserId(appUserId);
    }

    /**
     * Возвращает, существует ли у персонажа событие по его id
     */
    public Boolean hasEvent(Long characterId) {
        log.info("Get event existence for character id {}", characterId);
        return characterRepo.existsByIdAndCurrentEventIsNotNull(characterId);
    }

    /**
     * Сохраняет персонажа
     */
    @Transactional
    public Character save(Character character) {
        character.setName(character.getName().trim());
        log.info("Save character {}", character);
        return characterRepo.save(character);
    }
}
