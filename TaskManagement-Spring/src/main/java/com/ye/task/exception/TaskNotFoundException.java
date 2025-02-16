package com.ye.task.exception;

public class TaskNotFoundException extends RuntimeException{

    public TaskNotFoundException(String message) {
        super(message);
    }
}
