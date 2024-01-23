package com.ryangrillo.fida.todolist.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryangrillo.fida.todolist.model.Todo;
import com.ryangrillo.fida.todolist.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Test
    public void testAddTodos() throws Exception {
        List<String> todos = Arrays.asList("Todo1", "Todo2");

        mockMvc.perform(post("/api/v1/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(todos)))
                .andExpect(status().isOk());

        verify(todoService, times(1)).addTodos(todos);
    }

    @Test
    public void testAddTodosDetectsMaliciousContent() throws Exception {
        List<String> maliciousTodos = Arrays.asList("<script>alert('malicious')</script>", "Todo2");
        mockMvc.perform(post("/api/v1/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(maliciousTodos)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetTodos() throws Exception {
        List<Todo> todoList = List.of(
                Todo.builder()
                        .id(1L)
                        .description("Todo1")
                        .isCompleted(false)
                        .completedDate("12.23.2023")
                        .build(),

                Todo.builder()
                        .id(2L)
                        .description("Todo2")
                        .isCompleted(true)
                        .completedDate("10.17.2023")
                        .build());


        when(todoService.getAllTodos()).thenReturn(todoList);

        mockMvc.perform(get("/api/v1/todos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].description").value("Todo1"))
                .andExpect(jsonPath("$[0].isCompleted").value(false))
                .andExpect(jsonPath("$[0].completedDate").value("12.23.2023"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].description").value("Todo2"))
                .andExpect(jsonPath("$[1].isCompleted").value(true))
                .andExpect(jsonPath("$[1].completedDate").value("10.17.2023"));

        verify(todoService, times(1)).getAllTodos();
    }

    @Test
    public void testDeleteTodoById() throws Exception {
        Long todoId = 1L;

        mockMvc.perform(delete("/api/v1/todo/{id}", todoId))
                .andExpect(status().isOk());

        verify(todoService, times(1)).deleteTodo(todoId);
    }

    @Test
    public void testMarkAsComplete() throws Exception {
        Long todoId = 1L;

        Todo todo = Todo.builder()
                .id(1L)
                .description("Todo1")
                .isCompleted(true)
                .completedDate("06.14.2023")
                .build();

        when(todoService.markTodoAsCompleted(todoId)).thenReturn(todo);

        mockMvc.perform(put("/api/v1/todo/{id}", todoId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("Todo1"))
                .andExpect(jsonPath("$.isCompleted").value(true));

        verify(todoService, times(1)).markTodoAsCompleted(todoId);
    }
}