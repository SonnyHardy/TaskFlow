package com.sonny.taskflow.task.application.controller;

import com.sonny.taskflow.task.application.dto.CreateTaskRequest;
import com.sonny.taskflow.task.application.dto.TaskResponse;
import com.sonny.taskflow.task.domain.usecase.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    // The controller injects only the UseCases, no repositories
    private final CreateTaskUseCase createTaskUseCase;
    private final CompleteTaskUseCase completeTaskUseCase;
    private final GetTasksUseCase  getTasksUseCase;

    public TaskController(CreateTaskUseCase createTaskUseCase, CompleteTaskUseCase completeTaskUseCase,
                          GetTasksUseCase getTasksUseCase) {
        this.createTaskUseCase = createTaskUseCase;
        this.completeTaskUseCase = completeTaskUseCase;
        this.getTasksUseCase = getTasksUseCase;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody CreateTaskRequest request) {

        // DTO -> Command
        CreateTaskCommand command = CreateTaskCommand.fromTaskRequest(request);

        // Call the UseCase
        CreateTaskResult result = createTaskUseCase.execute(command);

        // Result -> DTO
        return ResponseEntity.status(HttpStatus.CREATED).body(TaskResponse.from(result));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Void> complete(@PathVariable UUID id) {
        completeTaskUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasksByAssignedTo(@RequestParam String assignedTo) {
        return ResponseEntity.ok(getTasksUseCase.execute(assignedTo)
                .stream()
                .map(TaskResponse::from)
                .toList());
    }

}
