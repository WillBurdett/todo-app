package com.will.todo_backend.service;

import com.will.todo_backend.model.api.TodoInput;
import com.will.todo_backend.model.api.TodoOutput;
import com.will.todo_backend.model.entity.TodoEntity;
import com.will.todo_backend.repository.TodoRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepo todoRepo;

    public TodoService(TodoRepo todoRepo) {
        this.todoRepo = todoRepo;
    }

    public List<TodoOutput> getAllTodos() {
        return todoRepo.findAll().stream().map(this::mapEntityToOutput).toList();
    }

    public TodoOutput createTodo(TodoInput todoInput) {
        TodoEntity entity = todoRepo.save(
                new TodoEntity(
                        todoInput.getTitle(),
                        todoInput.getDescription(),
                        todoInput.getDefcon(),
                        todoInput.getDueDate()));

        return mapEntityToOutput(entity);
    }

    private TodoOutput mapEntityToOutput(TodoEntity entity){
        return new TodoOutput(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getDefcon(),
                entity.getCreatedOn(),
                entity.getDueDate(),
                entity.isComplete()
        );
    }
}
