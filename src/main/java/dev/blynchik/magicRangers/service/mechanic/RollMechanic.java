package dev.blynchik.magicRangers.service.mechanic;

import dev.blynchik.magicRangers.model.storage.AppAttributes;
import dev.blynchik.magicRangers.model.storage.AppCharacter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

import static dev.blynchik.magicRangers.model.storage.AppAttributes.DECLINE;
import static dev.blynchik.magicRangers.model.storage.AppAttributes.TEXT;

@Service
@Slf4j
public class RollMechanic {

    private final Random random;

    @Autowired
    public RollMechanic(Random random) {
        this.random = random;
    }

    /**
     * Метод возвращает случайное значение атрибута от 0 до ограничения, либо
     * до соответствующего атрибута персонажа, если ограничения нет или оно <= 0
     */
    public Integer roll(AppAttributes attribute, Integer constraint, AppCharacter character) {
        log.info("Roll value for {} with constraint {}", attribute, constraint);
        if (constraint == null || constraint <= 0) return roll(attribute, character);
        switch (attribute) {
            case STR -> {
                return character.getStr() < constraint ?
                        roll(attribute, character) :
                        roll(attribute, constraint);
            }
            case INTL -> {
                return character.getIntl() < constraint ?
                        roll(attribute, character) :
                        roll(attribute, constraint);
            }
            case CHA -> {
                return character.getCha() < constraint ?
                        roll(attribute, character) :
                        roll(attribute, constraint);
            }
            default -> {
                return roll(attribute, 0);
            }
        }
    }


    /**
     * Метод возвращает случайное число от 0 до соответствующего значения атрибута персонажа
     */
    public Integer roll(AppAttributes attribute, AppCharacter character) {
        log.info("Roll value for {} with {}", attribute, character);
        switch (attribute) {
            case STR -> {
                return roll(attribute, character.getStr());
            }
            case INTL -> {
                return roll(attribute, character.getIntl());
            }
            case CHA -> {
                return roll(attribute, character.getCha());
            }
            default -> {
                return roll(attribute, 0);
            }
        }
    }


    /**
     * Метод возвращает случайное число от 0 до ограничения,
     * если атрибут или ограничение не подходят, то вернет null
     */
    public Integer roll(AppAttributes attribute, Integer constraint) {
        log.info("Roll value for {} with constraint {}", attribute, constraint);
        if (attribute == null || attribute.equals(TEXT) || attribute.equals(DECLINE) || constraint == null) return null;
        return roll(0, constraint);
    }

    /**
     * Метод возвращает случайное число от min до max (включая)
     */
    public Integer roll(int min, int max) {
        log.info("Roll value min={}, max={}", min, max);
        int value = random.nextInt(min, max + 1);
        log.info("Rolled value={}", value);
        return value;
    }
}
