package com.ryangrillo.fida.todolist.controller;

import com.ryangrillo.fida.todolist.constants.TodoConstants;
import com.ryangrillo.fida.todolist.exception.MaliciousInputException;
import com.ryangrillo.fida.todolist.model.Todo;
import com.ryangrillo.fida.todolist.service.TodoService;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ryangrillo.fida.todolist.constants.TodoConstants.TODOS_ENDPOINT;

@RestController
@RequestMapping("/api/v1")
public class TodoController {

    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);

    @Autowired
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping(path = TODOS_ENDPOINT)
    public List<String> addTodos(@RequestBody List<String> todos) {
        if (!Jsoup.isValid(todos.toString(), Safelist.basic())) {
            throw new MaliciousInputException("Input is possibly malicious");
        }
        logger.info("Received request to add todos: {}", todos);
        return todoService.addTodos(todos);
    }

    @GetMapping(path = TODOS_ENDPOINT)
    public List<Todo> getTodos() {
        logger.info("Received request to get all todos");
        List<Todo> todos = todoService.getAllTodos();
        logger.info("Returning {} todos", todos.size());
        return todos;
    }

    @DeleteMapping(path = TodoConstants.TODO_ID_PATH_VARIABLE)
    public void deleteTodoById(@PathVariable Long id) {
        todoService.deleteTodo(id);
        logger.info("Todo with id {} deleted successfully", id);
    }

    @PutMapping(path = TodoConstants.TODO_ID_PATH_VARIABLE)
    public Todo markAsComplete(@PathVariable Long id) {
        logger.info("Received request to mark todo with id {} as complete", id);
        Todo completedTodo = todoService.markTodoAsCompleted(id);
        logger.info("Todo marked as complete: {}", completedTodo);
        return completedTodo;
    }
}
