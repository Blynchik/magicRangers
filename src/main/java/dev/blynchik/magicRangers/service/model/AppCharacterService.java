package dev.blynchik.magicRangers.service.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.blynchik.magicRangers.exception.AppException;
import dev.blynchik.magicRangers.model.storage.AnimalName;
import dev.blynchik.magicRangers.model.storage.AppCharacter;
import dev.blynchik.magicRangers.model.storage.AppEvent;
import dev.blynchik.magicRangers.model.storage.ColorName;
import dev.blynchik.magicRangers.repo.AppCharacterRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static dev.blynchik.magicRangers.exception.ExceptionMessage.*;

@Service
@Slf4j
@Transactional(readOnly = true)
public class AppCharacterService {

    private final AppCharacterRepo characterRepo;
    private final ObjectMapper objectMapper;
    private final List<AnimalName> animalName;
    private final List<ColorName> colorName;

    @Autowired
    public AppCharacterService(AppCharacterRepo characterRepo,
                               ObjectMapper objectMapper,
                               List<AnimalName> animalName,
                               List<ColorName> colorName) {
        this.characterRepo = characterRepo;
        this.objectMapper = objectMapper;
        this.animalName = animalName;
        this.colorName = colorName;
    }

    /**
     * Генерирует три случайных числа от 70 до 160, чтобы их сумма не превысила 300
     */
    public int[] getRandomAttributes() {
        log.info("Get random attributes");
        int attributeSum = 90;
        int[] attributes = new int[]{70, 70, 70};
        int plusRandom;
        for (int i = 0; i < 2; i++) {
            plusRandom = ThreadLocalRandom.current().nextInt(0, attributeSum);
            attributes[i] += plusRandom;
            attributeSum -= plusRandom;
        }
        attributes[2] += attributeSum;
        return attributes;
    }

    /**
     * Создает случайного персонажа
     */
    @Transactional
    public AppCharacter createRandomCharacter(Long userId) {
        log.info("Get random character");
        int[] attributes = getRandomAttributes();
        return create(userId, new AppCharacter(null,
                getRandomName(),
                attributes[0],
                attributes[1],
                attributes[2]
        ));
    }

    /**
     * Подбирает персонажу случайное имя
     */
    public String getRandomName() {
        log.info("Get random name");
        AnimalName animal = animalName.get(ThreadLocalRandom.current().nextInt(animalName.size()));
        ColorName color = colorName.get(ThreadLocalRandom.current().nextInt(colorName.size()));
        return color.getName() + color.getEnds()[animal.getGender() - 1] + " " + animal.getName();
    }

    /**
     * Метод удаляет персонажа по id пользователя
     */
    @Transactional
    public void deleteByAppUserId(Long appUserId) {
        log.info("Delete by user id: {}", appUserId);
        characterRepo.deleteByAppUserId(appUserId);
    }

    /**
     * Метод удаляет персонажа по его id
     */
    @Transactional
    public void delete(Long characterId) {
        log.info("Delete character id: {}", characterId);
        characterRepo.deleteById(characterId);
    }

    /**
     * Метод обновляет атрибуты персонажа
     */
    @Transactional
    public void updateAttributes(Long characterId, Integer strValue, Integer intlValue, Integer chaValue) {
        log.info("Update attributes for character id:{}, str:{}, intl:{}, cha:{}", characterId, strValue, intlValue, chaValue);
        characterRepo.updateAttributesValue(characterId, strValue, intlValue, chaValue);
    }

    /**
     * Метод обновляет текущее событие персонажа
     * или выбрасывает исключение, если не смог этого сделать
     */
    @Transactional
    public void updateCurrentEvent(Long characterId, AppEvent event) {
        log.info("Update current event for character id:{}, event:{}", characterId, event);
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
        log.info("Save character {}", character);
        return characterRepo.save(character);
    }
}
