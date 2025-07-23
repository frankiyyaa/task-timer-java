package com.project.tasktimer.model;

import java.time.LocalDate;

public class Task extends AbstractEntity {

    private String title;
    private String description;
    private LocalDate dueDate;
    private boolean completed;
    private long categoryId;
    private long userId;

    public Task() {}

    public Task(String title, String description, LocalDate dueDate,
                long categoryId, long userId) {
        super();
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = false;
        this.categoryId = categoryId;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "[Task] " + title + " (Due: " + dueDate + ", Done: " + completed + ")";
    }
}
