package anais.springboot.sample.cafeteria.controller;

import anais.springboot.sample.cafeteria.exception.DataNotFoundException;
import anais.springboot.sample.cafeteria.exception.DuplicatedException;
import anais.springboot.sample.cafeteria.model.exceptional.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionMessage handleDataNotFoundException(DataNotFoundException e) {
        return e.getExceptionMessage();
    }

    @ExceptionHandler(DuplicatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage handleDuplicatedException(DuplicatedException e) {
        return e.getExceptionMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionMessage handleException(Exception e) {
        e.printStackTrace();
        return new ExceptionMessage("ERROR.CF500", "", Collections.emptyList());
    }
}
