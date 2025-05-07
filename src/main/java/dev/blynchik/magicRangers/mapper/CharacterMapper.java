package dev.blynchik.magicRangers.mapper;

import dev.blynchik.magicRangers.model.dto.CharacterRequest;
import dev.blynchik.magicRangers.model.dto.CharacterResponse;
import dev.blynchik.magicRangers.model.storage.Character;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class CharacterMapper {

    /**
     * Конвертирует dto для создания Character
     */
    public Character mapToEntity(Long appUserId, CharacterRequest dto) {
        log.info("Convert {} to {}", dto.getClass().getName(), Character.class.getName());
        return new Character(appUserId, dto.getName(), dto.getStr(), dto.getIntl(), dto.getCha(), LocalDateTime.now());
    }

    /**
     * Конвертирует Character в dto для ответа
     */
    public CharacterResponse mapToDto(Character character) {
        log.info("Convert {} to {}", character.getClass().getName(), CharacterResponse.class.getName());
        return new CharacterResponse(character.getName(),
                character.getStr(), character.getIntl(), character.getCha(),
                character.getCreatedAt());
    }
}
