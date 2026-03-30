package com.sonny.taskflow.task.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTaskRequest(
        @NotBlank @Size(min = 2, max = 255)
        String title,
        String description,
        @NotBlank
        String assignedTo
) {
}
