package com.will.todo_backend.service;

import com.will.todo_backend.model.api.TodoInput;
import com.will.todo_backend.model.api.TodoOutput;
import com.will.todo_backend.model.entity.TodoEntity;
import com.will.todo_backend.repository.TodoRepo;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import static com.will.todo_backend.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TodoServiceTest {

    @Mock
    private Clock clock;

    @Mock
    private TodoRepo todoRepo;

    @InjectMocks
    private TodoService undertest;

    private final Long ID = 1L;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        Instant fixedInstant = Instant.parse("2024-01-01T10:00:00Z");
        ZoneId fixedZone = ZoneOffset.UTC;

        when(clock.instant()).thenReturn(fixedInstant);
        when(clock.getZone()).thenReturn(fixedZone);
    }

    @Nested
    class get_all_todos_should {

        @Test
        void return_all_todos_when_todos_exist() {
            // given
            List<TodoEntity> todos = List.of(
                    createTodoEntity(1L),
                    createTodoEntity(2L)
            );
            when(todoRepo.findAll()).thenReturn(todos);

            // when
            List<TodoOutput> result = undertest.getAllTodos();

            // then
            assertEquals(2, result.size());
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
        void save_todo_then_return_output() {
            // given
            TodoInput input = createTodoInput();

            // when
            TodoOutput actualOutput = undertest.createTodo(input);

            // then
            TodoEntity expectedEntity = createTodoEntity(null);
            TodoOutput expectedOutput = createTodoOutput(null);

            assertEntitySavedOutputReturned(expectedEntity, expectedOutput, actualOutput);
        }
    }

    @Nested
    class update_todo_should {

        @Test
        void save_todo_entity_with_updated_fields_then_return_updated_output() {
            // given
            stubTodoRepoFindById(createTodoEntity(ID));
            TodoInput input = createTodoInput();
            input.setTitle("new title");

            // when
            TodoOutput actualOutput = undertest.updateTodo(ID, input);

            // then
            TodoEntity expectedEntity = createTodoEntity(ID);
            expectedEntity.setTitle("new title");
            TodoOutput expectedOutput = createTodoOutput(ID);
            expectedOutput.setTitle("new title");

            assertEntitySavedOutputReturned(expectedEntity, expectedOutput, actualOutput);
        }
    }

    @Nested
    class toggleComplete_should {

        @Test
        void save_entity_with_opposite_complete_status() {
            // given
            stubTodoRepoFindById(createTodoEntity(ID));

            // when
            TodoOutput actualOutput = undertest.toggleComplete(ID);

            // then
            TodoEntity expectedEntity = createTodoEntity(ID);;
            expectedEntity.setComplete(true);
            TodoOutput expectedOutput = createTodoOutput(ID);
            expectedOutput.setComplete(true);

            verify(todoRepo).save(expectedEntity);
            assertEquals(expectedOutput, actualOutput);
        }
    }

    private void assertEntitySavedOutputReturned(TodoEntity entity, TodoOutput expected, TodoOutput actual) {
        verify(todoRepo).save(entity);
        assertEquals(expected, actual);
    }

    private void stubTodoRepoFindById(TodoEntity entity) {
        when(todoRepo.findById(ID)).thenReturn(Optional.ofNullable(entity));
    }

}