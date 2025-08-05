package dev.blynchik.magicRangers.service.mechanic;

import dev.blynchik.magicRangers.model.storage.AppCharacter;
import dev.blynchik.magicRangers.model.storage.Reward;
import dev.blynchik.magicRangers.service.model.AppCharacterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RewardMechanic {

    private final AppCharacterService characterService;

    public RewardMechanic(AppCharacterService appCharacterService) {
        this.characterService = appCharacterService;
    }

    /**
     * Метод начисляет очки атрибутов, которые обозначены в награде
     */
    public AppCharacter setReward(AppCharacter character, List<Reward> rewards) {
        log.info("Reward character:{} with rewards:{}", character, rewards);
        if (rewards != null && !rewards.isEmpty()) {
            rewards.forEach(r -> {
                        switch (r.getType()) {
                            case STR -> character.setStr(character.getStr() + r.getValue());
                            case INTL -> character.setIntl(character.getIntl() + r.getValue());
                            case CHA -> character.setCha(character.getCha() + r.getValue());
                        }
                    }
            );
            characterService.updateAttributes(character.getId(), character.getStr(), character.getIntl(), character.getCha());
        }
        return character;
    }
}
