package com.sonny.taskflow.task.domain.usecase;

import com.sonny.taskflow.task.domain.entity.TaskStatus;

import java.util.UUID;

public record CreateTaskResult(
        UUID id,
        String title,
        TaskStatus status,
        String assignedTo
) {
}
