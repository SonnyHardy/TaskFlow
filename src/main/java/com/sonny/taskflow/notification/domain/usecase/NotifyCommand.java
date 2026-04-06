package com.sonny.taskflow.notification.domain.usecase;

public record NotifyCommand(
        String assignedTo,
        String message
) {
}
