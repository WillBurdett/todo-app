package com.will.todo_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "/todo")
public class TodoController {

    @GetMapping
    public Map<String,String> getTodos() {
        return Map.of("Welcome", "This is my todo app!");
    }
}
