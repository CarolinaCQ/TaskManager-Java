package com.project.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class HandlerException {

    @ExceptionHandler(value = {BadRequest.class})
    protected ResponseEntity<Object> handleBadRequest(BadRequest ex) {
        MessageException message = new MessageException(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFound.class})
    protected ResponseEntity<Object> handleBadRequest(NotFound ex) {
        MessageException message = new MessageException(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
