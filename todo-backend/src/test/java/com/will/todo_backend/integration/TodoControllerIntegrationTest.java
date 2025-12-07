package com.will.todo_backend.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.will.todo_backend.model.entity.TodoEntity;
import com.will.todo_backend.model.enums.Defcon;
import com.will.todo_backend.repository.TodoRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.*;
import java.util.List;

import static com.will.todo_backend.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class TodoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private TodoRepo todoRepo;

    @Autowired
    private Clock clock;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        dbReset();
    }

    @Nested
    class getAllTodos_should {

        @Test
        void return_all_todo_outputs_with_200() throws Exception {
            // given
            todoRepo.saveAll(List.of(
                    new TodoEntity("test title", "test description", Defcon.ONE, MOCKED_CREATED_ON, null),
                    new TodoEntity("test title", "test description", Defcon.ONE, MOCKED_CREATED_ON, null)
            ));

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
            // when
            var result = mockMvc.perform(post("/todo")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(getJsonAsString("input/valid_todo_input.json")))
                    .andReturn()
                    .getResponse();

            // then
            String expected = String.format(getJsonAsString("output/valid_todo_output.json"), LocalDate.now(clock));

            assertEquals(201, result.getStatus());
            assertJsonEquals(expected, result.getContentAsString());
        }
    }

    @Nested
    class updateTodo_should {

        @Test
        void return_todo_output_with_200() throws Exception {
            // given
            todoRepo.save(new TodoEntity("old title", "test description", Defcon.ONE, MOCKED_CREATED_ON, null));

            // when
            var result = mockMvc.perform(put("/todo/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(getJsonAsString("input/valid_todo_input.json")))
                    .andReturn()
                    .getResponse();

            // then
            String expected = String.format(getJsonAsString("output/valid_todo_output.json"), MOCKED_CREATED_ON);

            assertEquals(200, result.getStatus());
            assertJsonEquals(expected, result.getContentAsString());
        }
    }

    private void dbReset() {
        todoRepo.deleteAll();
        String resetGeneratedIdSQL = "ALTER SEQUENCE todo_id_seq RESTART WITH 1;";
        jdbcTemplate.execute(resetGeneratedIdSQL);
    }
}
