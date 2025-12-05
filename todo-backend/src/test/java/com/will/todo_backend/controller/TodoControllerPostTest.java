package com.will.todo_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.will.todo_backend.model.api.TodoInput;
import com.will.todo_backend.service.TodoService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.will.todo_backend.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(TodoController.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class TodoControllerPostTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper mapper;

    private TodoController undertest;

    @Nested
    class createTodo_should_{

        @Test
        void return_todo_output_with_201() throws Exception {
            // given
            TodoInput todoInput = createTodoInput();
            when(todoService.createTodo(todoInput)).thenReturn(createTodoOutput(1L));

            // when
            var result = performPostWith(getJsonAsString("input/valid_todo_input.json"));

            // then
            String expected = getJsonAsString("output/valid_todo_output.json");

            assertEquals(201, result.getStatus());
            assertJsonEquals(expected, result.getContentAsString());
        }

        @Test
        void return_422_when_title_blank() throws Exception {
            // when
            var result = performPostWith("""
                                    {
                                       "title": "",
                                       "description": "test description",
                                       "defcon": "1",
                                       "dueDate": null
                                    }
                                    """);

            // then
            assertEquals(422, result.getStatus());
        }

        @Test
        void return_422_when_description_blank() throws Exception {
            // when
            var result = performPostWith("""
                                    {
                                       "title": "test title",
                                       "description": "",
                                       "defcon": "1",
                                       "dueDate": null
                                    }
                                    """);
            // then
            assertEquals(422, result.getStatus());
        }

        @Test
        void return_422_when_invalid_defcon() throws Exception {
            // when
            var result = performPostWith("""
                                    {
                                       "title": "test title",
                                       "description": "test description",
                                       "defcon": "6",
                                       "dueDate": null
                                    }
                                    """);

            // then
            assertEquals(422, result.getStatus());
        }

        @Test
        void return_422_when_invalid_date_format() throws Exception {
            // when
            var result = performPostWith("""
                                    {
                                       "title": "test title",
                                       "description": "test description",
                                       "defcon": "1",
                                       "dueDate": 20-01-01
                                    }
                                    """);

            // then
            assertEquals(422, result.getStatus());
        }

        private MockHttpServletResponse performPostWith(String input) throws Exception {
            return mockMvc.perform(post("/todo")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(input))
                    .andReturn()
                    .getResponse();
        }
    }
}
