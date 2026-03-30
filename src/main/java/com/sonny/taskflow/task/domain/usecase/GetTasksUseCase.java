package com.sonny.taskflow.task.domain.usecase;

import com.sonny.taskflow.task.domain.port.TaskRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class GetTasksUseCase {

    private final TaskRepositoryPort taskRepositoryPort;   // Port, no JPA

    public GetTasksUseCase(TaskRepositoryPort taskRepositoryPort) {
        this.taskRepositoryPort = taskRepositoryPort;
    }

    @Transactional
    public List<GetTaskResult> execute(String assignedTo) {
        return taskRepositoryPort.findAllTasksByAssignedTo(assignedTo)
                .stream()
                .map(GetTaskResult::fromDomain)
                .toList();
    }
}
