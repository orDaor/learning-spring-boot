package com.ltp.contacts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/* @ControllerAdvice --> this is the global application handler, which can handle UNchecked exceptions (that are
 * derived from class "RuntimeException". We can still handle such exceptions with try/catch, but in that case the handler in
 * the @ControllerAdvice class will not run, and the "catch" block will run instead*/

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ContactNotFoundException.class)
    public ResponseEntity<Object> handleContactNotFoundException(ContactNotFoundException exception) {
        /*System.out.println(exception.getMessage());*/
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
