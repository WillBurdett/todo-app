package com.will.todo_backend.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.will.todo_backend.model.entity.TodoEntity;
import com.will.todo_backend.model.enums.Defcon;
import com.will.todo_backend.repository.TodoRepo;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import static com.will.todo_backend.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Import(TodoControllerIntegrationTest.TestClockConfig.class)
public class TodoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private TodoRepo todoRepo;

    @Autowired
    private Clock clock;

    @TestConfiguration
    static class TestClockConfig {
        @Primary
        @Bean
        public Clock clock() {
            return Clock.fixed(
                    Instant.parse("2024-01-01T00:00:00Z"),
                    ZoneOffset.UTC
            );
        }
    }

    @Nested
    class getAllTodos_should {

        @Test
        void return_all_todo_outputs_with_200() throws Exception {
            // given
            todoRepo.saveAll(List.of(
                    new TodoEntity("test title", "test description", Defcon.ONE, LocalDate.now(clock), null),
                    new TodoEntity("test title", "test description", Defcon.ONE, LocalDate.now(clock), null)
            ));

            // when
            var result = mockMvc.perform(get("/todo")).andReturn().getResponse();

            // then
            String expected = getJsonAsString("output/getAllTodos_valid.json");

            assertEquals(200, result.getStatus());
            assertJsonEquals(expected, result.getContentAsString());
        }
    }
}
