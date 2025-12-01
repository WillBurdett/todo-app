package com.will.todo_backend.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.will.todo_backend.model.api.TodoInput;
import com.will.todo_backend.model.api.TodoOutput;
import com.will.todo_backend.model.entity.TodoEntity;
import com.will.todo_backend.model.enums.Defcon;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Component
public class TestUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final Clock clock =
            Clock.fixed(Instant.parse("2024-01-01T10:00:00Z"), ZoneOffset.UTC);


    public static String getJsonAsString(String path) throws IOException {
        Path fileName
                = Path.of("src/test/java/com/will/todo_backend/" + path);
        return Files.readString(fileName);
    }

    public static void assertJsonEquals(String expected, String actual) throws JsonProcessingException {
        assertEquals(
                mapper.readTree(expected),
                mapper.readTree(actual)
        );
    }

    public static TodoInput createTodoInput() {
        return new TodoInput("test title", "test description", Defcon.ONE, null);
    }

    public static TodoEntity createTodoEntity(Long id) {
        return new TodoEntity(id,"test title", "test description", Defcon.ONE, LocalDate.now(clock), null, false);
    }

    public static TodoOutput createTodoOutput(Long id) {
        return new TodoOutput(id, "test title", "test description", Defcon.ONE, LocalDate.now(clock), null, false);
    }
}
