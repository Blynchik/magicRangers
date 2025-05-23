package dev.blynchik.magicRangers.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
@Slf4j
public class ValidationUIErrorUtil {

    //нужно для локализации ошибок валидирования
    private final MessageSource messageSource;

    @Autowired
    public ValidationUIErrorUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Проверяет наличие ошибок валидации и в зависимости от этого возвращает нужный ресурс
     */
    public boolean hasValidationErrors(BindingResult bindingResult) {
        log.warn("BindException {}", getErrorMap(bindingResult));
        return bindingResult.hasErrors();
    }

    /**
     * Возвращает мапу с ошибками валидации поле: локализованное сообщение
     */
    private Map<String, String> getErrorMap(BindingResult result) {
        Map<String, String> invalidParams = new LinkedHashMap<>();
        for (ObjectError error : result.getGlobalErrors()) {
            invalidParams.put(error.getObjectName(), getErrorMessage(error));
        }
        for (FieldError error : result.getFieldErrors()) {
            invalidParams.put(error.getField(), getErrorMessage(error));
        }
        return invalidParams;
    }

    /**
     * Возвращает локализованное сообщение об ошибке
     */
    private String getErrorMessage(ObjectError error) {
        return error.getCode() == null ? error.getDefaultMessage() :
                messageSource.getMessage(error.getCode(), error.getArguments(), error.getDefaultMessage(), LocaleContextHolder.getLocale());
    }
}
