package com.recycleforlife.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.recycleforlife.domain.ApiError;
import com.recycleforlife.domain.ErrorCode;
import com.recycleforlife.domain.exception.ApiException;
import com.recycleforlife.domain.exception.ForbiddenApiException;
import com.recycleforlife.domain.exception.UnauthorizedApiException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@RestControllerAdvice
public class RestExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            final HttpMessageNotReadableException ex,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request
    ) {
        final String fieldName = extractInvalidValueFieldName(ex);

        return ResponseEntity.badRequest()
                .body(
                        new ApiError()
                                .setCode(ErrorCode.VALIDATION_ERROR)
                                .setMessage("[%s] wrong format".formatted(fieldName))
                );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiError handle(final @NotNull ApiException ex) {
        log.debug(ex.getMessage(), ex);
        return ex.getApiError();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError handle(final @NotNull UnauthorizedApiException ex) {
        log.debug(ex.getMessage(), ex);
        return ex.getApiError();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handle(final @NotNull ForbiddenApiException ex) {
        log.debug(ex.getMessage(), ex);
        return ex.getApiError();
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            final @NotNull TypeMismatchException ex,
            final @NotNull HttpHeaders headers,
            final @NotNull HttpStatusCode status,
            final @NotNull WebRequest request
    ) {
        log.error(ex.getMessage(), ex);

        return ResponseEntity.badRequest()
                .body(
                        new ApiError()
                                .setCode(ErrorCode.VALIDATION_ERROR)
                                .setMessage("[%s] wrong format".formatted(((MethodArgumentTypeMismatchException) ex).getName()))
                );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final @NotNull MethodArgumentNotValidException ex,
            final @NotNull HttpHeaders headers,
            final @NotNull HttpStatusCode status,
            final @NotNull WebRequest request
    ) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.badRequest()
                .body(
                        new ApiError()
                                .setCode(ErrorCode.VALIDATION_ERROR)
                                .setMessage(
                                        ex.getBindingResult()
                                                .getFieldErrors()
                                                .stream()
                                                .findFirst()
                                                .map(e -> "[" + e.getField() + "]" + " " + e.getDefaultMessage())
                                                .orElse(
                                                        ex.getBindingResult()
                                                                .getAllErrors()
                                                                .stream()
                                                                .findFirst()
                                                                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                                                .orElse("error field did not detected")
                                                )

                                )
                );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handle(final @NotNull RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return new ApiError()
                .setCode(ErrorCode.INTERNAL_SERVER_ERROR)
                .setMessage(ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handle(final @NotNull ConstraintViolationException ex) {
        log.error(ex.getMessage(), ex);
        final String errorMessage = ex.getConstraintViolations()
                .stream()
                .findFirst()
                .map(violation -> "[" + StreamSupport.stream(violation.getPropertyPath().spliterator(), false)
                        .reduce((first, second) -> second).orElse(null) + "] " + violation.getMessage()
                )
                .orElse("impossible to detect wrong field");
        return new ApiError()
                .setCode(ErrorCode.VALIDATION_ERROR)
                .setMessage(errorMessage);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            final @NotNull MissingServletRequestParameterException ex,
            final @NotNull HttpHeaders headers,
            final @NotNull HttpStatusCode status,
            final @NotNull WebRequest request
    ) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.badRequest()
                .body(
                        new ApiError()
                                .setCode(ErrorCode.VALIDATION_ERROR)
                                .setMessage("[" + ex.getParameterName() + "] is missing")
                );
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(
            final @NotNull ServletRequestBindingException ex,
            final @NotNull HttpHeaders headers,
            final @NotNull HttpStatusCode status,
            final @NotNull WebRequest request
    ) {
        if (ex instanceof MissingRequestHeaderException missingRequestHeaderException) {
            return ResponseEntity.badRequest()
                    .body(
                            new ApiError()
                                    .setCode(ErrorCode.VALIDATION_ERROR)
                                    .setMessage("header - [%s] is missing".formatted(missingRequestHeaderException.getHeaderName()))
                    );
        }

        return ResponseEntity.badRequest()
                .body(
                        new ApiError()
                                .setCode(ErrorCode.VALIDATION_ERROR)
                                .setMessage(ex.getMessage())
                );
    }

    private String extractInvalidValueFieldName(@NotNull final HttpMessageNotReadableException ex) {
        if (ex.getCause() == null) {
            return "Required request body is missing";
        }

        if (ex.getCause() instanceof JsonMappingException jme) {
            return jme.getPath().stream()
                    .map(e -> {
                        final String fn = e.getFieldName();
                        final int index = e.getIndex();
                        return fn == null
                                ? "[" + index + "]"
                                : fn;
                    })
                    .collect(Collectors.joining("."));
        }

        return ex.getMessage();
    }
}
