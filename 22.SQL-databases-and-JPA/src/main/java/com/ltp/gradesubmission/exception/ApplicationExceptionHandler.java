package com.ltp.gradesubmission.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @Override //identical to method from workbook 8.3
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        //access object validation errors that are contained in the generated runtime exception
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        List<String> messages = new ArrayList<>();

        for (ObjectError error: errors) {
            messages.add(error.getDefaultMessage());
        }

        //The error messages are sent back in the response object
        ErrorResponse errorResponse = new ErrorResponse(messages);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({CourseNotFoundException.class, GradeNotFoundException.class, StudentNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundExceptions(RuntimeException ex) {

        List<String> messages = Arrays.asList( ex.getMessage() );

        ErrorResponse errorResponse = new ErrorResponse(messages);

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    //this exception will be thrown by an unsuccessful deletion in case the record to be deleted does not exist
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleDataAccessException(EmptyResultDataAccessException ex) {
        List<String> messages = Arrays.asList("Can not delete the resource because it does not exist");

        ErrorResponse errorResponse = new ErrorResponse(messages);

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    //this exception is thrown when we attemp to save a grade that has the same student_id/course_id value pair
    //of a grade already existing in the database
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {

        List<String> messages = Arrays.asList("Data Integrity Violation: we cannot process your request");

        ErrorResponse errorResponse = new ErrorResponse(messages);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }
}
