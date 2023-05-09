package com.ltp.contacts.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
/* @ControllerAdvice --> this is the global application handler, which can handle UNchecked exceptions (that are
* derived from class "RuntimeException". We can still handle such exceptions with try/catch, but in that case the handler in
* the @ControllerAdvice class will not run, and the "catch" block will run instead*/

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ContactNotFoundException.class)
    public ResponseEntity<Object> handleContactNotFoundException(ContactNotFoundException ex) {

        ErrorResponse error = new ErrorResponse(ex.getLocalizedMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /*  */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        /*returns list of errors from validation process*/
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();

        for (ObjectError error : errors) {
            System.out.println(error.getDefaultMessage());
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // error code = 400
    }
}
