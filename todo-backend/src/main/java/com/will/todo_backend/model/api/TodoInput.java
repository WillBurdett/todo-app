package com.will.todo_backend.model.api;

import com.will.todo_backend.model.enums.Defcon;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TodoInput {
    @NotBlank(message = "Title must not be empty")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;
    @NotBlank(message = "Description must not be empty")
    private String description;
    private Defcon defcon;
    private LocalDate dueDate;
}
