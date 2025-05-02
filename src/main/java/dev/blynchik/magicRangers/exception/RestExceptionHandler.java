package dev.blynchik.magicRangers.exception;

import jakarta.annotation.Nonnull;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Обработчик срабатывает на выбрасываемое исключение [AppException].
     */
    @ExceptionHandler(AppException.class)
    public ProblemDetail handleException(AppException ex, WebRequest request) {
        log.error("Intercepted AppException. Status: {}, Message: {}", ex.getStatus(), ex.getMessage());
        return createProblemDetail(ex.getStatus(), ex.getMessage(), request);
    }

    /**
     * Обработчик срабатывает, когда не удается прочесть тело входящего запроса.
     */
    @Override
    @Nonnull
    protected ResponseEntity<Object> handleHttpMessageNotReadable(@Nonnull HttpMessageNotReadableException ex,
                                                                  @Nonnull HttpHeaders headers,
                                                                  @Nonnull HttpStatusCode status,
                                                                  @Nonnull WebRequest request) {
        log.error("Intercepted HttpMessageNotReadable. Message: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(createProblemDetail(HttpStatus.BAD_REQUEST, ex.getMessage(), request));
    }

    /**
     * Обработчик срабатывает, когда присутствуют ошибки валидации в теле запроса.
     */
    @Override
    @Nonnull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
                                                                  @Nonnull HttpHeaders headers,
                                                                  @Nonnull HttpStatusCode status,
                                                                  @Nonnull WebRequest request) {
        var problemDetail = ex.getBody();
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getGlobalErrors().forEach(e -> {
            errors.put(e.getObjectName(), e.getDefaultMessage());
        });
        ex.getBindingResult().getFieldErrors().forEach(e -> {
            errors.put(e.getField(), e.getDefaultMessage());
        });
        log.error("Intercepted MethodArgumentNotValidException. Errors: {}", errors);
        problemDetail.setProperty("invalid_params", errors);
        problemDetail.setStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setInstance(URI.create(((ServletWebRequest) request).getRequest().getRequestURI()));
        return handleExceptionInternal(ex, problemDetail, headers, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Обработчик срабатывает, когда в методах restController-а есть параметры, помеченные @Valid @PathVariable и др.
     */
    @Override
    @Nonnull
    protected ResponseEntity<Object> handleHandlerMethodValidationException(@Nonnull HandlerMethodValidationException ex,
                                                                            @Nonnull HttpHeaders headers,
                                                                            @Nonnull HttpStatusCode status,
                                                                            @Nonnull WebRequest request) {
        var problemDetail = ex.getBody();
        Map<String, String> errors = new HashMap<>();
        ex.getParameterValidationResults().forEach(
                result -> result.getResolvableErrors().forEach(
                        e -> errors.put(result.getMethodParameter().getParameterName(), e.getDefaultMessage())));
        log.error("Intercepted HandlerMethodValidationException. Errors: {}", errors);
        problemDetail.setProperty("invalid_params", errors);
        problemDetail.setStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setInstance(URI.create(((ServletWebRequest) request).getRequest().getRequestURI()));
        return handleExceptionInternal(ex, problemDetail, headers, HttpStatus.BAD_REQUEST, request);
    }

    private static ProblemDetail createProblemDetail(HttpStatus status, String message, WebRequest request) {
        var pd = ProblemDetail.forStatusAndDetail(status, message);
        pd.setProperty("timestamp", Instant.now());
        pd.setInstance(URI.create(((ServletWebRequest) request).getRequest().getRequestURI()));
        return pd;
    }
}
