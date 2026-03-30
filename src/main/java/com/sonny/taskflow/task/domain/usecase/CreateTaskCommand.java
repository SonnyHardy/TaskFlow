package com.sonny.taskflow.task.domain.usecase;

import com.sonny.taskflow.task.application.dto.CreateTaskRequest;
import org.springframework.web.util.HtmlUtils;

public record CreateTaskCommand(
        String title,
        String description,
        String assignedTo
) {

    public static CreateTaskCommand fromTaskRequest(CreateTaskRequest request) {
        return new CreateTaskCommand(
                HtmlUtils.htmlEscape(request.title()),
                HtmlUtils.htmlEscape(request.description()),
                HtmlUtils.htmlEscape(request.assignedTo())
        );
    }
}
