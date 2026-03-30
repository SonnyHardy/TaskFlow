package com.sonny.taskflow.task.domain.event;

import java.time.Instant;
import java.util.UUID;

public record TaskCompletedEvent(
        UUID taskId,
        String assignedTo,
        Instant completedAt
) {

    public TaskCompletedEvent(UUID taskId, String assignedTo) {
        this(taskId, assignedTo, Instant.now());
    }
}
