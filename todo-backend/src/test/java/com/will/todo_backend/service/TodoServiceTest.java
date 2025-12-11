package com.will.todo_backend.service;

import com.will.todo_backend.exceptions.DueDateAlreadyPastException;
import com.will.todo_backend.exceptions.TodoNotFoundException;
import com.will.todo_backend.model.api.TodoInput;
import com.will.todo_backend.model.api.TodoOutput;
import com.will.todo_backend.model.entity.TodoEntity;
import com.will.todo_backend.model.enums.Defcon;
import com.will.todo_backend.repository.TodoRepo;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.*;
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

        @Test
        void throw_400_when_dueDate_is_present_but_has_already_past() {
            // given
            TodoInput input = createTodoInput();
            input.setDueDate(LocalDate.of(2023, 12, 31));

            // when + then
            Exception ex = assertThrows(DueDateAlreadyPastException.class, () -> {
                undertest.createTodo(input);
            });

            assertEquals("Cannot create todo: Due date has already past!", ex.getMessage());
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

        @Test
        void throw_404_when_todo_does_not_exist() {
            // given
            TodoInput input = createTodoInput();

            // when + then
            Exception ex = assertThrows(TodoNotFoundException.class, () -> {
                undertest.updateTodo(ID, input);
            });

            assertEquals("Todo not found with id: " + ID, ex.getMessage());
        }
    }

    @Nested
    class toggleComplete_should {

        @Test
        void save_entity_with_complete_status_true_and_set_completedOn() {
            // given
            stubTodoRepoFindById(createTodoEntity(ID));

            // when
            TodoOutput actualOutput = undertest.toggleComplete(ID);

            // then
            TodoOutput completedTodoOutput =
                    new TodoOutput(
                            ID,
                            "test title",
                            "test description",
                            Defcon.ONE,
                            LocalDate.now(clock),
                            null,
                            true,
                            LocalDate.now(clock));

            verify(todoRepo).save(completedTodoEntity());
            assertEquals(completedTodoOutput, actualOutput);
        }

        @Test
        void save_entity_with_complete_status_false_and_remove_completedOn() {
            // given
            stubTodoRepoFindById(completedTodoEntity());

            // when
            TodoOutput actualOutput = undertest.toggleComplete(ID);

            // then
            TodoEntity incompleteTodoEntity = createTodoEntity(ID);
            TodoOutput incompleteTodoOutput = createTodoOutput(ID);

            verify(todoRepo).save(incompleteTodoEntity);
            assertEquals(incompleteTodoOutput, actualOutput);
        }

        @Test
        void throw_404_when_todo_does_not_exist() {
            // when + then
            Exception ex = assertThrows(TodoNotFoundException.class, () -> {
                undertest.toggleComplete(ID);
            });

            assertEquals("Todo not found with id: " + ID, ex.getMessage());
        }

        private TodoEntity completedTodoEntity() {
            return new TodoEntity(
                    ID,
                    "test title",
                    "test description",
                    Defcon.ONE,
                    MOCKED_DATE,
                    null,
                    true,
                    MOCKED_DATE);
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