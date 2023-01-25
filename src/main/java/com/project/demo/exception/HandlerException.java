package com.project.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class HandlerException {

    @ExceptionHandler(value = {BadRequest.class})
    protected ResponseEntity<Object> handleBadRequest(BadRequest ex) {
        MessageException message = new MessageException(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFound.class})
    protected ResponseEntity<Object> handleBadRequest(NotFound ex) {
        MessageException message = new MessageException(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {Forbidden.class})
    protected ResponseEntity<Object> handleForbidden(Forbidden ex) {
        MessageException message = new MessageException(
                ex.getMessage(),
                HttpStatus.FORBIDDEN.value(),
                LocalDateTime.now());

        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }
}
