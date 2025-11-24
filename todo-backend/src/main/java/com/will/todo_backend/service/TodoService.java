package com.will.todo_backend.service;

import com.will.todo_backend.model.api.TodoInput;
import com.will.todo_backend.model.api.TodoOutput;
import com.will.todo_backend.model.entity.TodoEntity;
import com.will.todo_backend.repository.TodoRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public TodoOutput updateTodo(Long id, TodoInput todoInput) {
        Optional<TodoEntity> maybeEntity = todoRepo.findById(id);
        if (maybeEntity.isPresent()) {
            TodoEntity entity = maybeEntity.get();
            entity.setTitle(todoInput.getTitle());
            entity.setDescription(todoInput.getDescription());
            entity.setDefcon(todoInput.getDefcon());
            entity.setDueDate(todoInput.getDueDate());
            todoRepo.save(entity);
            return mapEntityToOutput(entity);
        }
        return null;
    }

    public TodoOutput toggleComplete(Long id){
        Optional<TodoEntity> maybeEntity = todoRepo.findById(id);
        if (maybeEntity.isPresent()) {
            TodoEntity entity = maybeEntity.get();
            entity.setComplete(!entity.isComplete());
            todoRepo.save(entity);
            return mapEntityToOutput(entity);
        }
        return null;
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
