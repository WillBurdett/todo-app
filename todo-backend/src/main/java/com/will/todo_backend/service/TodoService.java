package com.will.todo_backend.service;

import com.will.todo_backend.exceptions.DueDateAlreadyPastException;
import com.will.todo_backend.exceptions.TodoNotFoundException;
import com.will.todo_backend.model.api.TodoInput;
import com.will.todo_backend.model.api.TodoOutput;
import com.will.todo_backend.model.entity.TodoEntity;
import com.will.todo_backend.repository.TodoRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

@Service
public class TodoService {

    private final Clock clock;

    private final TodoRepo todoRepo;

    public TodoService(Clock clock, TodoRepo todoRepo) {
        this.clock = clock;
        this.todoRepo = todoRepo;
    }

    public List<TodoOutput> getAllTodos() {
        return todoRepo.findAll().stream().map(this::mapEntityToOutput).toList();
    }

    public TodoOutput createTodo(TodoInput todoInput) {
        validateDueDate(todoInput.getDueDate());
        return saveThenReturn(
                new TodoEntity(
                        todoInput.getTitle(),
                        todoInput.getDescription(),
                        todoInput.getDefcon(),
                        LocalDate.now(clock),
                        todoInput.getDueDate()));
    }

    public TodoOutput updateTodo(Long id, TodoInput todoInput) {
        TodoEntity entity = findTodoById(id);

        entity.setTitle(todoInput.getTitle());
        entity.setDescription(todoInput.getDescription());
        entity.setDefcon(todoInput.getDefcon());
        entity.setDueDate(todoInput.getDueDate());

        return saveThenReturn(entity);
    }

    public void deleteTodo(Long id) {
        TodoEntity entity = findTodoById(id);
        todoRepo.delete(entity);
    }

    public TodoOutput toggleComplete(Long id){
        TodoEntity entity = findTodoById(id);

        entity.setComplete(!entity.isComplete());
        return saveThenReturn(entity);
    }

    private TodoEntity findTodoById(Long id) {
        return todoRepo.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + id));
    }

    private void validateDueDate(LocalDate dueDate) {
        if (dueDate != null && dueDate.isBefore(LocalDate.now(clock))) {
            throw new DueDateAlreadyPastException("Cannot create todo: Due date has already past!");
        }
    }

    @Transactional
    private TodoOutput saveThenReturn(TodoEntity entity) {
        todoRepo.save(entity);
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
