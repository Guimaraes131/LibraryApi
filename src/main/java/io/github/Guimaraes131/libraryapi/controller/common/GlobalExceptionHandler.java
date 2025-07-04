package io.github.Guimaraes131.libraryapi.controller.common;

import io.github.Guimaraes131.libraryapi.controller.dto.ErrorResponse;
import io.github.Guimaraes131.libraryapi.controller.dto.FieldError;
import io.github.Guimaraes131.libraryapi.exception.BookPriceNotSetException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var fieldErrors = e.getFieldErrors();

        var errors = fieldErrors
                .stream()
                .map(fe ->
                        new FieldError(fe.getField(), fe.getDefaultMessage())
                ).toList();

        return new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation Error", errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException() {
        return ErrorResponse.badRequest("Cannot parse the JSON to an entity.");
    }

    @ExceptionHandler(BookPriceNotSetException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleBookPriceNotSetException(BookPriceNotSetException e) {
        return new ErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation Error.",
                List.of(new FieldError("price", e.getMessage()))
        );
    }
}
