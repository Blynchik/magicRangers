package dev.blynchik.magicRangers.validation.validator;

import dev.blynchik.magicRangers.model.dto.request.AppEventOptionRequest;
import dev.blynchik.magicRangers.model.dto.request.AppEventRequest;
import dev.blynchik.magicRangers.validation.annotaion.CountAttempts;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CountAttemptsValidator implements ConstraintValidator<CountAttempts, AppEventRequest> {

    @Override
    public boolean isValid(AppEventRequest value, ConstraintValidatorContext context) {
        if (value == null) return true;
        if (value.getOptionRequests() == null) return true;
        int sumOfAttempts = value.getOptionRequests().stream()
                .mapToInt(AppEventOptionRequest::getRemainingAttempts)
                .sum();
        return value.getCommonAttempts() == sumOfAttempts;
    }
}
