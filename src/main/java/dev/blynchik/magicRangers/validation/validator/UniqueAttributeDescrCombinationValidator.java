package dev.blynchik.magicRangers.validation.validator;

import dev.blynchik.magicRangers.model.dto.EventOptionRequest;
import dev.blynchik.magicRangers.validation.annotaion.UniqueAttributeDescrCombination;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UniqueAttributeDescrCombinationValidator implements ConstraintValidator<UniqueAttributeDescrCombination, List<EventOptionRequest>> {

    @Override
    public boolean isValid(List<EventOptionRequest> value, ConstraintValidatorContext context) {
        if (value == null) return true;

        Set<String> uniqueCombinations = new HashSet<>();
        for (EventOptionRequest option : value) {
            if (option == null) continue;
            String key = option.getAttribute() + "::" + option.getDescr();
            if (!uniqueCombinations.add(key)) {
                return false;
            }
        }
        return true;
    }
}
