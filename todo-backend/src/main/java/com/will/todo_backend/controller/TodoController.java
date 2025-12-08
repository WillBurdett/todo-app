package com.will.todo_backend.controller;

import com.will.todo_backend.model.api.TodoInput;
import com.will.todo_backend.model.api.TodoOutput;
import com.will.todo_backend.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @CrossOrigin("http://localhost:5173/")
    @GetMapping
    public ResponseEntity<List<TodoOutput>> getAllTodos() {
        List<TodoOutput> responseBody = todoService.getAllTodos();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TodoOutput> createTodo(@Valid @RequestBody TodoInput todoInput) {
        validateTodoInput(todoInput);
        TodoOutput responseBody = todoService.createTodo(todoInput);
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<TodoOutput> updateTodo(@PathVariable Long id, @Valid @RequestBody TodoInput todoInput) {
        TodoOutput responseBody = todoService.updateTodo(id, todoInput);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "/toggle-complete/{id}")
    public ResponseEntity<TodoOutput> toggleComplete(@PathVariable Long id) {
        TodoOutput responseBody = todoService.toggleComplete(id);
        return  new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    private void validateTodoInput(TodoInput input) {

    }
}
