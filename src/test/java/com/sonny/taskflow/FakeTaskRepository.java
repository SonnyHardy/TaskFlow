package com.sonny.taskflow;

import com.sonny.taskflow.task.domain.entity.Task;
import com.sonny.taskflow.task.domain.port.TaskRepositoryPort;

import java.util.*;

public class FakeTaskRepository implements TaskRepositoryPort {

    private final Map<UUID, Task> store = new HashMap<>();

    @Override
    public void saveTask(Task task) {
        store.put(task.getId(), task);
    }

    @Override
    public java.util.Optional<Task> findTaskById(UUID taskId) {
        return Optional.ofNullable(store.get(taskId));
    }

    @Override
    public List<Task> findAllTasksByAssignedTo(String assignedTo) {
        return store.values().stream()
                .filter(task -> task.getAssignedTo().equals(assignedTo))
                .toList();
    }

}
