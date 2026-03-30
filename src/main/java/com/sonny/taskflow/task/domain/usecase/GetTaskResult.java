package com.sonny.taskflow.task.domain.usecase;

import com.sonny.taskflow.task.domain.entity.Task;
import com.sonny.taskflow.task.domain.entity.TaskStatus;

import java.util.UUID;

public record GetTaskResult(
        UUID id,
        String title,
        String description,
        TaskStatus status,
        String assignedTo
) {

    public static GetTaskResult fromDomain(Task task) {
        return new GetTaskResult(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getAssignedTo()
        );
    }
}
