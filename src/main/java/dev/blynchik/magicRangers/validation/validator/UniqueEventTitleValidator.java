package dev.blynchik.magicRangers.validation.validator;

import dev.blynchik.magicRangers.service.model.EventService;
import dev.blynchik.magicRangers.validation.annotaion.UniqueEventTitle;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueEventTitleValidator implements ConstraintValidator<UniqueEventTitle, String> {

    private final EventService eventService;

    @Autowired
    public UniqueEventTitleValidator(EventService eventService) {
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
