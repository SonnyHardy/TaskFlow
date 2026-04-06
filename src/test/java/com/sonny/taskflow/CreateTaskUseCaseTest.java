package com.sonny.taskflow;

import com.sonny.taskflow.task.domain.entity.TaskStatus;
import com.sonny.taskflow.task.domain.usecase.CreateTaskCommand;
import com.sonny.taskflow.task.domain.usecase.CreateTaskResult;
import com.sonny.taskflow.task.domain.usecase.CreateTaskUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.*;

// 0 spring, 0 DB, 0 Mockito. Only Java
public class CreateTaskUseCaseTest {

    FakeTaskRepository fakeRepo = new FakeTaskRepository();
    ApplicationEventPublisher fakeEvents = event -> {}; // no-op
    CreateTaskUseCase useCase = new CreateTaskUseCase(fakeRepo, fakeEvents);

    @Test
    void shouldCreateTaskWithPendingStatus() {
        CreateTaskCommand command = new CreateTaskCommand(
                "Do tests",
                "Unit tests DDD",
                "Sonny"
        );
        CreateTaskResult result = useCase.execute(command);
        assertNotNull(result.id());
        assertEquals("Do tests", result.title());
        assertEquals("Sonny", result.assignedTo());
        assertEquals(TaskStatus.PENDING, result.status());
    }

    @Test
    void shouldThrowExceptionWhenTitleIsBlank() {
        CreateTaskCommand command = new CreateTaskCommand("", null, "Sonny");
        assertThrows(IllegalArgumentException.class, () -> useCase.execute(command));
    }
}
