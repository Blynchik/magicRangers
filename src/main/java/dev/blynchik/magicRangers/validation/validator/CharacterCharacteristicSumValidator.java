package dev.blynchik.magicRangers.validation.validator;

import dev.blynchik.magicRangers.model.dto.CharacterRequest;
import dev.blynchik.magicRangers.validation.annotaion.ValidCharacterCharacteristicSum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CharacterCharacteristicSumValidator implements ConstraintValidator<ValidCharacterCharacteristicSum, CharacterRequest> {

    private final int expectedSum = 300;
    private final MessageSource messageSource;

    @Autowired
    public CharacterCharacteristicSumValidator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @Override
    public boolean isValid(CharacterRequest value, ConstraintValidatorContext context) {
        if (value.getStr() == null || value.getIntl() == null || value.getCha() == null) {
            return true; // Пусть другие аннотации сработают
        }
        int actualSum = value.getStr() + value.getIntl() + value.getCha();
        if (actualSum == expectedSum) return true;

        context.disableDefaultConstraintViolation();
        String message;
        if (actualSum < expectedSum) {
            message = messageSource.getMessage(
                    "character.constraint.message.invalidCharacteristicSum.less",
                    new Object[]{expectedSum - actualSum},
                    LocaleContextHolder.getLocale());
        } else {
            message = messageSource.getMessage(
                    "character.constraint.message.invalidCharacteristicSum.more",
                    new Object[]{actualSum - expectedSum},
                    LocaleContextHolder.getLocale());
        }
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
        return false;
    }
}
