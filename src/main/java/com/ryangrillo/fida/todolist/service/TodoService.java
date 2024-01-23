package com.ryangrillo.fida.todolist.service;

import com.ryangrillo.fida.todolist.entity.TodoEntity;
import com.ryangrillo.fida.todolist.exception.TodoNotFoundException;
import com.ryangrillo.fida.todolist.model.Todo;
import com.ryangrillo.fida.todolist.repository.TodoRepository;
import com.ryangrillo.fida.todolist.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public record TodoService(@Autowired TodoRepository todoRepository) {

    private static final Logger logger = LoggerFactory.getLogger(TodoService.class);

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<String> addTodos(List<String> todos) {
        List<TodoEntity> todoEntities = todos.stream()
                .map(this::convertToTodoEntity)
                .collect(Collectors.toList());
        todoRepository.saveAll(todoEntities);
        return todos;
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll().stream()
                .map(this::convertToTodoModel)
                .collect(Collectors.toList());
    }

    public void deleteTodo(Long id) {
        if (todoRepository.existsById(id)) {
            try {
                todoRepository.deleteById(id);
            } catch (Exception e) {
                logger.error("Error deleting todo with id: {}", id, e);
                throw new TodoNotFoundException("Error deleting todo with id: " + id);
            }
        } else {
            logger.warn("Todo not found with id: {}", id);
            throw new TodoNotFoundException("Todo not found with id: " + id);
        }
    }

    public Todo markTodoAsCompleted(Long todoId) {
        Optional<TodoEntity> optionalTodo = todoRepository.findById(todoId);
        if (optionalTodo.isPresent()) {
            TodoEntity todo = optionalTodo.get();
            todo.setIsCompleted(true);
            todo.setCompletionDate(DateUtils.formatInstant(Instant.now()));
            return convertToTodoModel(todoRepository.save(todo));
        } else {
            logger.warn("Todo not found with id: " + todoId);
            throw new TodoNotFoundException("Todo not found with id: " + todoId);
        }
    }

    private TodoEntity convertToTodoEntity(String todo) {
        return TodoEntity.builder()
                .description(todo)
                .completionDate(null)
                .isCompleted(false)
                .insertedAt(Instant.now())
                .build();
    }

    private Todo convertToTodoModel(TodoEntity todoEntity) {
        return Todo.builder()
                .id(todoEntity.getId())
                .description(todoEntity.getDescription())
                .completedDate(todoEntity.getCompletionDate())
                .isCompleted(todoEntity.getIsCompleted())
                .build();
    }
}
