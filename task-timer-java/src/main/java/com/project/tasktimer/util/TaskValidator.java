package com.project.tasktimer.util;

import com.project.tasktimer.model.Task;

public final class TaskValidator {

    private TaskValidator() {}

    public static void validate(Task task) {
        if (task == null) {
            throw new InvalidTaskException("Task cannot be null");
        }
        String title = task.getTitle();
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidTaskException("Task title cannot be empty");
        }
    }
}
