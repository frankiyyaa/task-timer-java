package com.project.tasktimer.service;

import com.project.tasktimer.dao.TaskDAO;
import com.project.tasktimer.model.Task;
import com.project.tasktimer.util.InvalidTaskException;
import com.project.tasktimer.util.TaskValidator;

import java.sql.SQLException;
import java.util.List;

public class TaskService {

    private final TaskDAO taskDAO = new TaskDAO();

    public long createTask(Task task) {
        TaskValidator.validate(task);
        try {
            return taskDAO.create(task);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create task", e);
        }
    }

    public Task getTaskById(long id) {
        try {
            return taskDAO.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch task by ID", e);
        }
    }

    public List<Task> getTasksByUser(long userId) {
        try {
            return taskDAO.findAllByUser(userId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch tasks by user", e);
        }
    }

    public boolean updateTask(Task task) {
        TaskValidator.validate(task);
        try {
            return taskDAO.update(task);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update task", e);
        }
    }

    public boolean deleteTask(long id) {
        try {
            return taskDAO.delete(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete task", e);
        }
    }

    public boolean markTaskCompleted(long id, boolean completed) {
        try {
            return taskDAO.markCompleted(id, completed);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update task status", e);
        }
    }
}
