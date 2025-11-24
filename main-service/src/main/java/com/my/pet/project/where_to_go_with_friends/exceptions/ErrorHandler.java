package com.my.pet.project.where_to_go_with_friends.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final ValidationException e) {
        String reason = "Ошибка валидации.";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return buildException(e, reason, status);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        String reason = "Искомый объект не найден.";
        HttpStatus status = HttpStatus.NOT_FOUND;
        return buildException(e, reason, status);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleCommonException(final CommonException e) {
        String reason = "Возникло исключение.";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return buildException(e, reason, status);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflictException(final ConflictException e) {
        String reason = "Запрос не может быть выполнен из-за конфликта с некоторыми правилами, " +
                "установленными для ресурсов.";
        HttpStatus status = HttpStatus.CONFLICT;
        return buildException(e, reason, status);
    }

    private ErrorResponse buildException(Exception e, String reason, HttpStatus status) {
        return ErrorResponse.builder()
                .errors(List.of(e.getMessage()))
                .message(e.getMessage())
                .reason(reason)
                .status(status.toString())
                .build();
    }
}