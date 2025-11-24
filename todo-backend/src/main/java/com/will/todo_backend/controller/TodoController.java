package com.will.todo_backend.controller;

import com.will.todo_backend.model.api.TodoInput;
import com.will.todo_backend.model.api.TodoOutput;
import com.will.todo_backend.service.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<TodoOutput> getAllTodos() {
        return todoService.getAllTodos();
    }

    @PostMapping
    public TodoOutput createTodo(@RequestBody TodoInput todoInput) {
        return todoService.createTodo(todoInput);
    }

    @PutMapping(path = "/{id}")
    public TodoOutput updateTodo(@PathVariable Long id, @RequestBody TodoInput todoInput) {
        return todoService.updateTodo(id, todoInput);
    }

    @PutMapping(path = "/toggle-complete/{id}")
    public TodoOutput toggleComplete(@PathVariable Long id) {
        return todoService.toggleComplete(id);
    }
}
