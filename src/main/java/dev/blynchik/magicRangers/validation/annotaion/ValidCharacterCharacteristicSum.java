package dev.blynchik.magicRangers.validation.annotaion;

import dev.blynchik.magicRangers.validation.validator.CharacterCharacteristicSumValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Аннотация для проверки суммы характеристик персонажа.
 * Применяется на уровне класса и проверяет, что сумма значений полей str, intl и cha
 * равна заданному значению (по умолчанию 300).
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CharacterCharacteristicSumValidator.class)
@Documented
public @interface ValidCharacterCharacteristicSum {
    String message() default "{character.constraint.message.invalidCharacteristicSum}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
