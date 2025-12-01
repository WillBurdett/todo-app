package com.will.todo_backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class DueDateAlreadyPastException extends RuntimeException {
    public DueDateAlreadyPastException(String message) {
        super(message);
    }
}
