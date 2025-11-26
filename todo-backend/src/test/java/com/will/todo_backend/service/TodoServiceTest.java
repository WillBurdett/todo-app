package com.will.todo_backend.service;

import com.will.todo_backend.model.api.TodoOutput;
import com.will.todo_backend.model.entity.TodoEntity;
import com.will.todo_backend.model.enums.Defcon;
import com.will.todo_backend.repository.TodoRepo;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TodoServiceTest {

    @Mock
    private TodoRepo todoRepo;

    @InjectMocks
    private TodoService undertest;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    class get_all_todos {
        @Test
        void returns_all_todos() {
            // given
            List<TodoEntity> todos = List.of(
                    createTodoEntityWithTitle("task 1"),
                    createTodoEntityWithTitle("task 2")
            );
            when(todoRepo.findAll()).thenReturn(todos);

            // when
            List<TodoOutput> result = undertest.getAllTodos();

            // then
            assertEquals(2, result.size());
            assertEquals("task 1", result.get(0).getTitle());
            assertEquals("task 2", result.get(1).getTitle());
            verify(todoRepo, times(1)).findAll();
        }
    }

    @Test
    void createTodo() {
    }

    @Test
    void updateTodo() {
    }

    @Test
    void toggleComplete() {
    }

    private TodoEntity createTodoEntityWithTitle(String title) {
        return new TodoEntity(title, "test entity", Defcon.ONE, null);
    }
}