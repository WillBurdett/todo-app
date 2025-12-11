package com.will.todo_backend.model.entity;

import com.will.todo_backend.model.enums.Defcon;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "todo_seq")
    @SequenceGenerator(name = "todo_seq", sequenceName = "todo_id_seq", allocationSize = 1)
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
    @Column
    private boolean complete;
    @Column
    private LocalDate completedOn;


    public TodoEntity() {
    }

    public TodoEntity(String title, String description, Defcon defcon, LocalDate createdOn, @Nullable LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.defcon = defcon;
        this.createdOn = createdOn;
        this.dueDate = dueDate;
        this.complete = false;
    }
}

