package com.will.todo_backend.service;

import com.will.todo_backend.model.api.TodoInput;
import com.will.todo_backend.model.api.TodoOutput;
import com.will.todo_backend.model.entity.TodoEntity;
import com.will.todo_backend.model.enums.Defcon;
import com.will.todo_backend.repository.TodoRepo;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    class get_all_todos_should {

        @Test
        void return_all_todos_when_todos_exist() {
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

        @Test
        void return_an_empty_list_when_no_todos_exist() {
            // given
            List<TodoEntity> todos = List.of();
            when(todoRepo.findAll()).thenReturn(todos);

            // when
            List<TodoOutput> result = undertest.getAllTodos();

            // then
            assertEquals(List.of(), result);
            verify(todoRepo, times(1)).findAll();
        }
    }

    @Nested
    class create_todo_should {

        @Test
        void create_todo_then_return_output() {
            // given
            TodoInput input = createTodoInputWithTitle("task 1");

            // when
            TodoOutput actualOutput = undertest.createTodo(input);

            // then
            TodoEntity expectedEntity = createTodoEntityWithTitle("task 1");
            TodoOutput expectedOutput = createTodoOutputWithTitle("task 1");

            verify(todoRepo).save(expectedEntity);
            assertEquals(expectedOutput, actualOutput);
        }
    }

    @Nested
    class update_todo_should {

        @BeforeEach
        void setup() {
            when(todoRepo.findById(1L)).thenReturn(Optional.of(createTodoEntityWithTitle("task 1")));
        }

        @Test
        void update_todo_entity_then_return_updated_output() {
            // given
            TodoInput input = createTodoInputWithTitle("task 2");

            // when
            TodoOutput actualOutput = undertest.updateTodo(1L, input);

            // then
            TodoEntity expectedEntity = createTodoEntityWithTitle("task 2");
            TodoOutput expectedOutput = createTodoOutputWithTitle("task 2");

            verify(todoRepo).save(expectedEntity);
            assertEquals(expectedOutput, actualOutput);
        }
    }

    @Test
    void toggleComplete() {
    }

    private TodoEntity createTodoEntityWithTitle(String title) {
        return new TodoEntity(title, "test todo", Defcon.ONE, null);
    }

    private TodoInput createTodoInputWithTitle(String title) {
        return new TodoInput(title, "test todo", Defcon.ONE, null);
    }

    private TodoOutput createTodoOutputWithTitle(String title) {
        return new TodoOutput(null, title, "test todo", Defcon.ONE, LocalDate.now(), null, false);
    }
}