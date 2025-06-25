package dev.blynchik.magicRangers.validation.validator;

import dev.blynchik.magicRangers.model.dto.EventOptionRequest;
import dev.blynchik.magicRangers.model.dto.EventRequest;
import dev.blynchik.magicRangers.validation.annotaion.UniqueEventOptions;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.HashSet;
import java.util.Set;

public class UniqueEventOptionsValidator implements ConstraintValidator<UniqueEventOptions, EventRequest> {

    private final MessageSource messageSource;

    @Autowired
    public UniqueEventOptionsValidator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public boolean isValid(EventRequest value, ConstraintValidatorContext context) {
        if (value == null || value.getOptionRequests() == null) return true;
        Set<EventOptionRequest> uniqueOptions = new HashSet<>();
        boolean isValid = true;
        String message = messageSource.getMessage(
                "character.constraint.message.invalidCharacteristicSum.less",
                new Object[]{},
                LocaleContextHolder.getLocale());
        for (int i = 0; i < value.getOptionRequests().size(); i++) {
            if (!uniqueOptions.add(value.getOptionRequests().get(i))) {
                context.disableDefaultConstraintViolation();
                context
                        .buildConstraintViolationWithTemplate(message)
                        .addPropertyNode("optionRequests")
                        .addBeanNode()
                        .inIterable().atIndex(i)
                        .addConstraintViolation();
                isValid = false;
            }
        }
        return isValid;
    }
}
