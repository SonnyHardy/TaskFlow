package com.sonny.taskflow.notification.application.listener;

import com.sonny.taskflow.notification.domain.usecase.NotifyCommand;
import com.sonny.taskflow.notification.domain.usecase.NotifyUserUseCase;
import com.sonny.taskflow.task.domain.event.TaskCompletedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TaskEventListener {

    private final NotifyUserUseCase notifyUserUseCase;

    public TaskEventListener(NotifyUserUseCase notifyUserUseCase) {
        this.notifyUserUseCase = notifyUserUseCase;
    }

    // Synchron - in same transaction like the UseCase that has published
    @EventListener
    public void onTaskCompleted(TaskCompletedEvent event) throws InterruptedException {
        Thread.sleep(1000L);
        notifyUserUseCase.execute(new NotifyCommand(
                event.assignedTo(),
                "Your task " + event.taskId() + " has been completed at " + event.completedAt()
        ));
    }

    // Use @Asynch for slow operations (emails, webhooks, etc.)
    @Async("notificationExecutor")
    @EventListener
    public void onTaskCompletedSendEmail(TaskCompletedEvent event) throws InterruptedException {
        // Simulate sending email
        Thread.sleep(2000L);
        System.out.println("Sending email to " + event.assignedTo() + " about task " + event.taskId() + " completion.");
    }

}
