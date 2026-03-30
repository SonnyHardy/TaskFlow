package com.sonny.taskflow.task.domain.usecase;

import com.sonny.taskflow.task.domain.entity.Task;
import com.sonny.taskflow.task.domain.event.TaskCompletedEvent;
import com.sonny.taskflow.task.domain.exception.TaskNotFoundException;
import com.sonny.taskflow.task.domain.port.TaskRepositoryPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class CompleteTaskUseCase {

    private final TaskRepositoryPort  taskRepositoryPort;
    private final ApplicationEventPublisher eventPublisher;

    public CompleteTaskUseCase(TaskRepositoryPort taskRepositoryPort, ApplicationEventPublisher eventPublisher) {
        this.taskRepositoryPort = taskRepositoryPort;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void execute(UUID taskId) {
        // We load the task
        Task task = taskRepositoryPort.findTaskById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));

        task.complete();  // Throw IllegalStateException when already completed

        // Persist the new state
        taskRepositoryPort.saveTask(task);

        // Publish an event when the task is completed. We don't know who is listening
        eventPublisher.publishEvent(new TaskCompletedEvent(task.getId(), task.getAssignedTo()));
    }

}
