package com.sonny.taskflow.task.infrastructure.config;

import com.sonny.taskflow.task.domain.port.TaskRepositoryPort;
import com.sonny.taskflow.task.domain.usecase.CompleteTaskUseCase;
import com.sonny.taskflow.task.domain.usecase.CreateTaskUseCase;
import com.sonny.taskflow.task.domain.usecase.GetTasksUseCase;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskConfig {

    // Spring finds TaskRepositoryAdapter because it is @Component
    // and implements TaskRepositoryPort.
    // We declare the UseCase as @Bean by injecting the port.

    @Bean
    public CreateTaskUseCase createTaskUseCase(TaskRepositoryPort taskRepositoryPort,
                                               ApplicationEventPublisher eventPublisher) {
        return new CreateTaskUseCase(taskRepositoryPort, eventPublisher);
    }

    @Bean
    public CompleteTaskUseCase completeTaskUseCase(TaskRepositoryPort taskRepositoryPort,
                               ApplicationEventPublisher eventPublisher) {
        return new CompleteTaskUseCase(taskRepositoryPort, eventPublisher);
    }

    @Bean
    public GetTasksUseCase getTasksUseCase(TaskRepositoryPort taskRepositoryPort) {
        return new GetTasksUseCase(taskRepositoryPort);
    }
}
