package dev.blynchik.magicRangers.mapper;

import dev.blynchik.magicRangers.model.dto.request.AppCharacterRequest;
import dev.blynchik.magicRangers.model.dto.response.AppCharacterResponse;
import dev.blynchik.magicRangers.model.storage.AppCharacter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AppCharacterMapper {

    /**
     * Конвертирует dto для создания Character
     */
    public AppCharacter mapToEntity(Long appUserId, AppCharacterRequest dto) {
        log.info("Convert {} to {}", dto.getClass().getName(), AppCharacter.class.getName());
        return new AppCharacter(appUserId, dto.getName(), dto.getStr(), dto.getIntl(), dto.getCha());
    }

    /**
     * Конвертирует Character в dto для ответа
     */
    public AppCharacterResponse mapToDto(AppCharacter character) {
        log.info("Convert {} to {}", character.getClass().getName(), AppCharacterResponse.class.getName());
        return new AppCharacterResponse(character.getName(),
                character.getStr(), character.getIntl(), character.getCha(),
                character.getCreatedAt());
    }
}
