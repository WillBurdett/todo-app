package com.will.todo_backend.config;

import com.will.todo_backend.exceptions.TodoNotFoundException;
import com.will.todo_backend.model.api.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
        return createThenReturnHttpCode(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TodoNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleClassInfoNotFoundExceptions(Exception ex, WebRequest request) throws Exception {
        return createThenReturnHttpCode(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDetails> handleMessageNotReadableExceptions(HttpMessageNotReadableException ex, WebRequest request) {
        return createThenReturnHttpCode(ex, request, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ErrorDetails> handleDateTimeParseExceptions(DateTimeParseException ex, WebRequest request) {
        return createThenReturnHttpCode(ex, request, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidationExceptions(MethodArgumentNotValidException ex) {

        ObjectError first = ex.getBindingResult().getAllErrors().get(0);

        ErrorDetails error = new ErrorDetails(
                first.getDefaultMessage(),
                "Validation failed",
                LocalDateTime.now(ZoneId.of("UTC"))
        );

        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private ResponseEntity<ErrorDetails> createThenReturnHttpCode(Exception ex, WebRequest req, HttpStatus status) {
        ErrorDetails error = new ErrorDetails(
                ex.getMessage(),
                req.getDescription(false),
                LocalDateTime.now(ZoneId.of("UTC"))
        );

        return new ResponseEntity<>(error, status);
    }
}
