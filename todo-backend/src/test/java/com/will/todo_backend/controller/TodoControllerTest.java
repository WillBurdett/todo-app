package com.will.todo_backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.will.todo_backend.model.api.TodoInput;
import com.will.todo_backend.model.api.TodoOutput;
import com.will.todo_backend.model.enums.Defcon;
import com.will.todo_backend.service.TodoService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(TodoController.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TodoService service;

    @Autowired
    private ObjectMapper mapper;

    private TodoController undertest;

    @Nested
    class getAllTodos_should {

        @Test
        void return_all_todo_outputs_with_200() throws Exception {
            // given
            when(service.getAllTodos()).thenReturn(List.of(createTodoOutput(1L), createTodoOutput(2L)));

            // when
            var result = mockMvc.perform(get("/todo")).andReturn().getResponse();

            // then
            String expected = getJsonAsString("output/getAllTodos_valid.json");

            assertEquals(200, result.getStatus());
            assertJsonEquals(expected, result.getContentAsString());
        }
    }

    @Nested
    class createTodo_should_{

        @Test
        void return_todo_output_with_201() throws Exception {
            // given
            TodoInput todoInput = createTodoInput();
            when(service.createTodo(todoInput)).thenReturn(createTodoOutput(1L));

            // when
            var result = mockMvc.perform(post("/todo")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(getJsonAsString("input/valid_todo_input.json")))
                    .andReturn()
                    .getResponse();

            // then
            String expected = getJsonAsString("output/valid_todo_output.json");

            assertEquals(201, result.getStatus());
            assertJsonEquals(expected, result.getContentAsString());
        }
    }

    @Nested
    class updateTodo_should {

        @Test
        void return_todo_output_with_200() throws Exception {
            // given
            TodoInput todoInput = createTodoInput();
            when(service.updateTodo(1L, todoInput)).thenReturn(createTodoOutput(1L));

            // when
            var result = mockMvc.perform(put("/todo/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(getJsonAsString("input/valid_todo_input.json")))
                    .andReturn()
                    .getResponse();

            // then
            String expected = getJsonAsString("output/valid_todo_output.json");

            assertEquals(200, result.getStatus());
            assertJsonEquals(expected, result.getContentAsString());
        }
    }

    @Nested
    class deleteTodo_should {

        @Test
        void return_204() throws Exception {
            // when
            var result = mockMvc.perform(delete("/todo/1"))
                    .andReturn()
                    .getResponse();

            // then
            verify(service).deleteTodo(1L);
            assertEquals(204, result.getStatus());
        }
    }

    @Nested
    class toggleComplete_should {

        @Test
        void return_todo_output_with_200() throws Exception {
            // given
            when(service.toggleComplete(1L)).thenReturn(createTodoOutput(1L));

            // when
            var result = mockMvc.perform(put("/todo/toggle-complete/1"))
                    .andReturn()
                    .getResponse();

            // then
            String expected = getJsonAsString("output/valid_todo_output.json");

            assertEquals(200, result.getStatus());
            assertJsonEquals(expected, result.getContentAsString());
        }
    }

    private void assertJsonEquals(String expected, String actual) throws JsonProcessingException {
        assertEquals(
                mapper.readTree(expected),
                mapper.readTree(actual)
        );
    }

    private String getJsonAsString(String path) throws IOException {
        Path fileName
                = Path.of("src/test/java/com/will/todo_backend/" + path);
        return Files.readString(fileName);
    }

    private TodoInput createTodoInput() {
        return new TodoInput("test title", "test description", Defcon.ONE, null);
    }

    private TodoOutput createTodoOutput(Long id) {
        return new TodoOutput(id, "test title", "test description", Defcon.ONE, LocalDate.of(2000,1,1), null, false);
    }
}