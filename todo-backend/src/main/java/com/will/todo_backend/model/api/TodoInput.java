package com.will.todo_backend.model.api;

import com.will.todo_backend.model.enums.Defcon;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TodoInput {
    private String title;
    private String description;
    private Defcon defcon;
    private LocalDate dueDate;
}
