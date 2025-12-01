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

import java.time.LocalDate;
import java.util.List;

import static com.will.todo_backend.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(TodoController.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class TodoControllerGetTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper mapper;

    private TodoController undertest;

    @Nested
    class getAllTodos_should {

        @Test
        void return_all_todo_outputs_with_200() throws Exception {
            // given
            when(todoService.getAllTodos()).thenReturn(List.of(createTodoOutput(1L), createTodoOutput(2L)));

            // when
            var result = mockMvc.perform(get("/todo")).andReturn().getResponse();

            // then
            LocalDate date = LocalDate.now();
            String expected = String.format(getJsonAsString("output/getAllTodos_valid.json"), date, date);

            assertEquals(200, result.getStatus());
            assertJsonEquals(expected, result.getContentAsString());
        }
    }
}
