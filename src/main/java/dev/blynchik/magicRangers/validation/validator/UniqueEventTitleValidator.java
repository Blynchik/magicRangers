package dev.blynchik.magicRangers.validation.validator;

import dev.blynchik.magicRangers.service.model.AppEventService;
import dev.blynchik.magicRangers.validation.annotaion.UniqueEventTitle;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueEventTitleValidator implements ConstraintValidator<UniqueEventTitle, String> {

    private final AppEventService eventService;

    @Autowired
    public UniqueEventTitleValidator(AppEventService eventService) {
        this.eventService = eventService;
    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty() || value.isBlank()) {
            return true;
        }
        return !eventService.existsByTitle(value);
    }
}
