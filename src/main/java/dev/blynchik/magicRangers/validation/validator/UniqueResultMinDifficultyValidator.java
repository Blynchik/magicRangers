package dev.blynchik.magicRangers.validation.validator;

import dev.blynchik.magicRangers.model.dto.EventOptionResultSetRequest;
import dev.blynchik.magicRangers.validation.annotaion.UniqueResultMinDifficulty;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UniqueResultMinDifficultyValidator implements ConstraintValidator<UniqueResultMinDifficulty, List<EventOptionResultSetRequest>> {

    @Override
    public boolean isValid(List<EventOptionResultSetRequest> value, ConstraintValidatorContext context) {
        if (value == null) return true;

        Set<Integer> uniqueCombinations = new HashSet<>();
        for (EventOptionResultSetRequest option : value) {
            if (option == null) continue;
            Integer key = option.getMinDifficulty();
            if (!uniqueCombinations.add(key)) {
                return false;
            }
        }
        return true;
    }
}
