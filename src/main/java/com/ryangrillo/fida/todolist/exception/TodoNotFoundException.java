package com.ryangrillo.fida.todolist.exception;

public class TodoNotFoundException extends RuntimeException{
    public TodoNotFoundException(String message) {
        super(message);
    }
}
