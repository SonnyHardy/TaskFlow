package com.sonny.taskflow;

import com.sonny.taskflow.task.domain.entity.Task;
import com.sonny.taskflow.task.domain.entity.TaskStatus;
import com.sonny.taskflow.task.domain.event.TaskCompletedEvent;
import com.sonny.taskflow.task.domain.usecase.CompleteTaskUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class CompleteTaskUseCaseTest {

    FakeTaskRepository fakeRepo = new FakeTaskRepository();
    List<Object> publishedEvents = new ArrayList<>();
    ApplicationEventPublisher fakeEvents = publishedEvents::add; // capture published events
    CompleteTaskUseCase useCase = new CompleteTaskUseCase(fakeRepo, fakeEvents);

    @Test
    void shouldCompleteTaskAndPublishEvent() {
        Task task = Task.create("Write tests", "Write unit tests for CompleteTaskUseCase", "Sonny");
        fakeRepo.saveTask(task);

        useCase.execute(task.getId());

        Task updated;
        if (fakeRepo.findTaskById(task.getId()).isEmpty()) {
            assertThrows(NoSuchElementException.class, () -> fakeRepo.findTaskById(task.getId()));
        }
        updated = fakeRepo.findTaskById(task.getId()).get();
        assertEquals(TaskStatus.COMPLETED, updated.getStatus());
        assertEquals(1, publishedEvents.size());
        assertInstanceOf(TaskCompletedEvent.class, publishedEvents.getFirst());
    }

    @Test
    void shouldThrowExceptionWhenTaskAlreadyCompleted() {
        Task task = Task.create("Write tests", "Write unit tests for complete", "Sonny");
        task.complete();
        fakeRepo.saveTask(task);

        assertThrows(IllegalStateException.class, () -> useCase.execute(task.getId()));
    }
}
