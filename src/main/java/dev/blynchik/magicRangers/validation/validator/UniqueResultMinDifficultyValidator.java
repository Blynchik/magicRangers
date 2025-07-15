package dev.blynchik.magicRangers.validation.validator;

import dev.blynchik.magicRangers.model.dto.request.AppEventOptionResultListRequest;
import dev.blynchik.magicRangers.validation.annotaion.UniqueResultMinDifficulty;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UniqueResultMinDifficultyValidator implements ConstraintValidator<UniqueResultMinDifficulty, List<AppEventOptionResultListRequest>> {

    @Override
    public boolean isValid(List<AppEventOptionResultListRequest> value, ConstraintValidatorContext context) {
        if (value == null) return true;

        Set<Integer> uniqueCombinations = new HashSet<>();
        for (AppEventOptionResultListRequest option : value) {
            if (option == null) continue;
            Integer key = option.getMinDifficulty();
            if (!uniqueCombinations.add(key)) {
                return false;
            }
        }
        return true;
    }
}
