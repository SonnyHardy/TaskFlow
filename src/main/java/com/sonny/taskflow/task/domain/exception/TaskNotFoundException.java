package com.sonny.taskflow.task.domain.exception;

import java.util.UUID;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(UUID taskId) {
        super("Task with id " + taskId.toString() + " not found");
    }
}
