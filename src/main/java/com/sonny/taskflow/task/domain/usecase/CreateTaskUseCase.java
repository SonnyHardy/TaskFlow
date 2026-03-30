package com.sonny.taskflow.task.domain.usecase;

import com.sonny.taskflow.task.domain.entity.Task;
import com.sonny.taskflow.task.domain.port.TaskRepositoryPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

// No spring, only java
// A usecase is a business logic. It receives a command (input values),
// manages entities and ports, returns a result.
public class CreateTaskUseCase {

    private final TaskRepositoryPort taskRepositoryPort;   // Port, no JPA
    private final ApplicationEventPublisher eventPublisher;   // Spring (exception acceptable)

    public CreateTaskUseCase(TaskRepositoryPort taskRepositoryPort, ApplicationEventPublisher eventPublisher) {
        this.taskRepositoryPort = taskRepositoryPort;
        this.eventPublisher = eventPublisher;
    }
    @Transactional
    public CreateTaskResult execute(CreateTaskCommand command) {
        // We create the entity via the factory method
        Task task = Task.create(
                command.title(),
                command.description(),
                command.assignedTo()
        );

        // Persist the entity via the port (We don't know that it is JPA)
        taskRepositoryPort.saveTask(task);

        // We return the result
        return new CreateTaskResult(
                task.getId(),
                task.getTitle(),
                task.getStatus()
        );

    }

}
