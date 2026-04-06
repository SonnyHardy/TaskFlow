package com.sonny.taskflow.task.application.dto;

import com.sonny.taskflow.task.domain.usecase.CreateTaskResult;
import com.sonny.taskflow.task.domain.usecase.GetTaskResult;

import java.util.UUID;

public record TaskResponse(
        UUID id,
        String title,
        String status,
        String assignedTo
) {

    public static TaskResponse from(CreateTaskResult taskResult) {
        return new TaskResponse(
                taskResult.id(),
                taskResult.title(),
                taskResult.status().name(),
                taskResult.assignedTo()
        );
    }

    public static TaskResponse from(GetTaskResult taskResult) {
        return new TaskResponse(
                taskResult.id(),
                taskResult.title(),
                taskResult.status().name(),
                taskResult.assignedTo()
        );
    }
}
