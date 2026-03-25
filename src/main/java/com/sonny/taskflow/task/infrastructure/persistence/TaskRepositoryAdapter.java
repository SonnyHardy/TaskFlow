package com.sonny.taskflow.task.infrastructure.persistence;

import com.sonny.taskflow.task.domain.entity.Task;
import com.sonny.taskflow.task.domain.port.TaskRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class TaskRepositoryAdapter implements TaskRepositoryPort {

    private final TaskJpaRepository taskJpaRepository;

    public TaskRepositoryAdapter(TaskJpaRepository taskJpaRepository) {
        this.taskJpaRepository = taskJpaRepository;
    }

    @Override
    public void saveTask(Task task) {
        // Convert domain Task to JPA entity and save using the repository
        TaskJpaEntity taskJpaEntity = TaskJpaEntity.fromDomain(task);
        taskJpaRepository.save(taskJpaEntity);
    }

    @Override
    public Optional<Task> findTaskById(UUID id) {
        return taskJpaRepository.findById(id).map(TaskJpaEntity::toDomain);
    }

    @Override
    public List<Task> findAllTasksByAssignedTo(String assignedTo) {
        return taskJpaRepository.findByAssignedTo(assignedTo)
                .stream()
                .map(TaskJpaEntity::toDomain)
                .toList();
    }
}
