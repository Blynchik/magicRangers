package dev.blynchik.magicRangers.validation.validator;

import dev.blynchik.magicRangers.model.dto.request.AppEventOptionRequest;
import dev.blynchik.magicRangers.validation.annotaion.UniqueAttributeDescrCombination;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UniqueAttributeDescrCombinationValidator implements ConstraintValidator<UniqueAttributeDescrCombination, List<AppEventOptionRequest>> {

    @Override
    public boolean isValid(List<AppEventOptionRequest> value, ConstraintValidatorContext context) {
        if (value == null) return true;

        Set<String> uniqueCombinations = new HashSet<>();
        for (AppEventOptionRequest option : value) {
            if (option == null) continue;
            String key = option.getAttribute() + "::" + option.getDescr();
            if (!uniqueCombinations.add(key)) {
                return false;
            }
        }
        return true;
    }
}
