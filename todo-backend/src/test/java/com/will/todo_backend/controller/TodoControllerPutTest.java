package com.will.todo_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.will.todo_backend.model.api.TodoInput;
import com.will.todo_backend.service.TodoService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static com.will.todo_backend.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(TodoController.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TodoControllerPutTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper mapper;

    private TodoController undertest;

    @Nested
    class updateTodo_should {

        @Test
        void return_todo_output_with_200() throws Exception {
            // given
            TodoInput todoInput = createTodoInput();
            when(todoService.updateTodo(1L, todoInput)).thenReturn(createTodoOutput(1L));

            // when
            var result = performPutWith(getJsonAsString("input/valid_todo_input.json"));

            // then
            String expected = getJsonAsString("output/valid_todo_output.json");

            assertEquals(200, result.getStatus());
            assertJsonEquals(expected, result.getContentAsString());
        }

        @Test
        void return_422_when_title_blank() throws Exception {
            // when
            var result = performPutWith("""
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
            var result = performPutWith("""
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
            var result = performPutWith("""
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
            var result = performPutWith("""
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

        private MockHttpServletResponse performPutWith(String input) throws Exception {
            return mockMvc.perform(put("/todo/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(input))
                    .andReturn()
                    .getResponse();
        }
    }

    @Nested
    class toggleComplete_should {

        @Test
        void return_todo_output_with_200() throws Exception {
            // given
            when(todoService.toggleComplete(1L)).thenReturn(createTodoOutput(1L));

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
}