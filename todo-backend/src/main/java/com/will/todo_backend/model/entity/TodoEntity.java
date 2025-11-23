package com.will.todo_backend.model.entity;

import com.will.todo_backend.model.enums.Defcon;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.ZoneId;

@Entity
@Data
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private Defcon defcon;
    @Column
    private LocalDate createdOn;
    @Column
    private LocalDate dueDate;
    private boolean complete;


    public TodoEntity() {
    }

    public TodoEntity(String title, String description, Defcon defcon, @Nullable LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.defcon = defcon;
        this.createdOn = LocalDate.now(ZoneId.of("UTC"));
        this.dueDate = dueDate;
        this.complete = false;
    }
}
