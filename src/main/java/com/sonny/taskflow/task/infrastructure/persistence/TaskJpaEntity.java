package com.sonny.taskflow.task.infrastructure.persistence;

import com.sonny.taskflow.task.domain.entity.Task;
import com.sonny.taskflow.task.domain.entity.TaskStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tasks")
public class TaskJpaEntity {

    @Id
    private UUID id;
    private String title;
    private String description;
    private String assignedTo;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    private Instant createdAt;
    private Instant completedAt;

    // Mapping infrastructure -> domain
    public Task toDomain() {
        return Task.reconstruct(id, title, description, assignedTo, status, createdAt, completedAt);
    }

    // Mapping domain -> infrastructure
    public static TaskJpaEntity fromDomain(Task task) {
        TaskJpaEntity taskJpaEntity = new TaskJpaEntity();
        taskJpaEntity.id = task.getId();
        taskJpaEntity.title = task.getTitle();
        taskJpaEntity.description = task.getDescription();
        taskJpaEntity.assignedTo = task.getAssignedTo();
        taskJpaEntity.status = task.getStatus();
        taskJpaEntity.createdAt = task.getCreatedAt();
        taskJpaEntity.completedAt = task.getCompletedAt();
        return taskJpaEntity;
    }
}
