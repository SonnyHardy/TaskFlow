package com.sonny.taskflow.task.domain.port;

import com.sonny.taskflow.task.domain.entity.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// No spring, only Java, this is a pure domain port interface for the repository
public interface TaskRepositoryPort {

    void saveTask(Task task);

    Optional<Task> findTaskById(UUID id);

    List<Task> findAllTasksByAssignedTo(String assignedTo);
}
