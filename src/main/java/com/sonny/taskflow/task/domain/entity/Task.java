package com.sonny.taskflow.task.domain.entity;

import java.time.Instant;
import java.util.UUID;

// No @Entity annotation, No Spring, only Java
public class Task {

    private final UUID id;
    private String title;
    private String description;
    private String assignedTo;
    private TaskStatus status;  // PENDING, IN_PROGRESS, COMPLETED
    private final Instant createdAt;
    private Instant completedAt;

    // private constructor to enforce the use of factory method
    private Task(UUID id, String title, String description, String assignedTo, Instant createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.assignedTo = assignedTo;
        this.status = TaskStatus.PENDING;
        this.createdAt = createdAt;
    }

    // Factory method to create a new Task
    public static Task create(String title, String description, String assignedTo) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        return new Task(UUID.randomUUID(), title, description, assignedTo, Instant.now());
    }

    // business logic to complete a task
    public void complete() {
        if (this.status == TaskStatus.COMPLETED) throw new IllegalStateException("Task has already been completed");
        this.status = TaskStatus.COMPLETED;
        this.completedAt = Instant.now();
    }

    public static Task reconstruct(UUID id, String title, String description, String assignedTo, TaskStatus status, Instant createdAt, Instant completedAt) {
        Task task = new Task(id, title, description, assignedTo, createdAt);
        task.status = status;
        task.completedAt = completedAt;
        return task;
    }

    // Only Getters (No public setters to enforce the use of business logic)
    public UUID getId() {return id;}
    public String getTitle() {return title;}

    public String getDescription() {return description;}

    public String getAssignedTo() {return assignedTo;}

    public TaskStatus getStatus() {return status;}

    public Instant getCreatedAt() {return createdAt;}

    public Instant getCompletedAt() {return completedAt;}

}
