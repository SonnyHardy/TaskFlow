package com.sonny.taskflow.notification.domain.usecase;

import org.springframework.transaction.annotation.Transactional;

public class NotifyUserUseCase {
        // This use case is responsible for sending notifications to users.
        // It can be triggered by events (e.g., TaskCompletedEvent) or by other use cases.

    @Transactional
    public void execute(NotifyCommand command) {
        System.out.println(command);
    }
}
