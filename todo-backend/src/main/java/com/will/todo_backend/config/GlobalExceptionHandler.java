package com.will.todo_backend.config;

import com.will.todo_backend.exceptions.TodoNotFoundException;
import com.will.todo_backend.model.api.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneId;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //ALL EXCEPTIONS
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
        ErrorDetails err = new ErrorDetails(
                ex.getMessage(),
                request.getDescription(false),
                LocalDateTime.now(ZoneId.of("UTC")));

        return new ResponseEntity<ErrorDetails>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // TODO NOT FOUND EXCEPTION
    @ExceptionHandler(TodoNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleClassInfoNotFoundExceptions(Exception ex, WebRequest request) throws Exception {
        ErrorDetails err = new ErrorDetails(
                ex.getMessage(),
                request.getDescription(false),
                LocalDateTime.now(ZoneId.of("UTC")));

        return new ResponseEntity<ErrorDetails>(err, HttpStatus.NOT_FOUND);
    }
}
