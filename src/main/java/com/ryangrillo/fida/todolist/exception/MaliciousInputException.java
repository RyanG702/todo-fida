package com.ryangrillo.fida.todolist.exception;

public class MaliciousInputException extends RuntimeException{
    public MaliciousInputException(String message) {
        super(message);
    }
}
