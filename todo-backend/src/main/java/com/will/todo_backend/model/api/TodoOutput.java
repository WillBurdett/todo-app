package com.will.todo_backend.model.api;

import com.will.todo_backend.model.enums.Defcon;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TodoOutput {
    private Long id;
    private String title;
    private String description;
    private Defcon defcon;
    private LocalDate createdOn;
    private LocalDate dueDate;
    private boolean complete;
    private LocalDate completedOn;
}
