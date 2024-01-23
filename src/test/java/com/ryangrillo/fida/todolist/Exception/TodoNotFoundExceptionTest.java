package com.ryangrillo.fida.todolist.Exception;

import com.ryangrillo.fida.todolist.exception.TodoNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoNotFoundExceptionTest {

    @Test
    public void testTodoNotFoundExceptionMessage() {
        String errorMessage = "Todo not found";
        TodoNotFoundException exception = new TodoNotFoundException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }

}
