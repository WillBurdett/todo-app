package com.will.todo_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.will.todo_backend.service.TodoService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@WebMvcTest(TodoController.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class TodoControllerDeleteTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper mapper;

    private TodoController undertest;

    @Nested
    class deleteTodo_should {

        @Test
        void return_204() throws Exception {
            // when
            var result = mockMvc.perform(delete("/todo/1"))
                    .andReturn()
                    .getResponse();

            // then
            verify(todoService).deleteTodo(1L);
            assertEquals(204, result.getStatus());
        }
    }
}
