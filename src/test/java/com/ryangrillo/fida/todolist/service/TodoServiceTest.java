package com.ryangrillo.fida.todolist.service;

import com.ryangrillo.fida.todolist.entity.TodoEntity;
import com.ryangrillo.fida.todolist.exception.TodoNotFoundException;
import com.ryangrillo.fida.todolist.model.Todo;
import com.ryangrillo.fida.todolist.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
public class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @Test
    public void testAddTodos() {
        List<String> todos = Arrays.asList("Todo1", "Todo2", "Todo3");

        todoService.addTodos(todos);

        verify(todoRepository, times(1)).saveAll(anyList());
    }

    @Test
    public void testGetAllTodos() {

        when(todoRepository.findAll()).thenReturn(Arrays.asList(
                new TodoEntity(1L, "Todo1", false, null, Instant.now()),
                new TodoEntity(2L, "Todo2", true, "22.01.2024", Instant.now())
        ));

        List<Todo> result = todoService.getAllTodos();

        assertEquals(2, result.size());
    }


    @Test
    public void testDeleteTodo() {
        Long todoId = 1L;

        when(todoRepository.existsById(todoId)).thenReturn(true);

        todoService.deleteTodo(todoId);

        verify(todoRepository, times(1)).deleteById(todoId);
    }

    @Test
    public void testDeleteTodoNotFound() {
        Long todoId = 1L;
        doThrow(new TodoNotFoundException("Todo not found")).when(todoRepository).deleteById(todoId);
        assertThrows(TodoNotFoundException.class, () -> todoService.deleteTodo(todoId));
    }

    @Test
    public void testMarkTodoAsCompleted() {
        Long todoId = 1L;
        TodoEntity todoEntity = new TodoEntity(1L, "Todo1", false, null, Instant.now());

        when(todoRepository.findById(todoId)).thenReturn(Optional.of(todoEntity));
        when(todoRepository.save(any(TodoEntity.class))).thenReturn(todoEntity);

        Todo result = todoService.markTodoAsCompleted(todoId);

        assertTrue(result.getIsCompleted());
        assertNotNull(result.getCompletedDate());
    }

    @Test
    public void testMarkTodoAsCompletedNotFound() {
        Long todoId = 1L;

        when(todoRepository.findById(todoId)).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class, () -> todoService.markTodoAsCompleted(todoId));
    }
}
