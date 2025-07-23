package com.project.tasktimer.dao;

import com.project.tasktimer.model.Task;
import com.project.tasktimer.util.TaskValidator;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    private static final String INSERT_SQL = "INSERT INTO tasks (title, description, due_date, completed, category_id, user_id) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM tasks WHERE id = ?";
    private static final String SELECT_BY_USER_SQL = "SELECT * FROM tasks WHERE user_id = ?";
    private static final String UPDATE_SQL = "UPDATE tasks SET title = ?, description = ?, due_date = ?, completed = ?, category_id = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM tasks WHERE id = ?";
    private static final String MARK_COMPLETED_SQL = "UPDATE tasks SET completed = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";

    public long create(Task task) {
        TaskValidator.validate(task);
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, task.getTitle());
            pst.setString(2, task.getDescription());
            pst.setString(3, task.getDueDate() != null ? task.getDueDate().toString() : null);
            pst.setInt(4, task.isCompleted() ? 1 : 0);
            pst.setLong(5, task.getCategoryId());
            pst.setLong(6, task.getUserId());
            pst.executeUpdate();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    long id = rs.getLong(1);
                    task.setId(id);
                    return id;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB error while creating task", e);
        }
        return -1;
    }

    public Task findById(long id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB error while fetching task", e);
        }
        return null;
    }

    public List<Task> findAllByUser(long userId) {
        List<Task> tasks = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(SELECT_BY_USER_SQL)) {

            pst.setLong(1, userId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    tasks.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB error while fetching tasks", e);
        }
        return tasks;
    }

    public boolean update(Task task) {
        TaskValidator.validate(task);
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(UPDATE_SQL)) {

            pst.setString(1, task.getTitle());
            pst.setString(2, task.getDescription());
            pst.setString(3, task.getDueDate() != null ? task.getDueDate().toString() : null);
            pst.setInt(4, task.isCompleted() ? 1 : 0);
            pst.setLong(5, task.getCategoryId());
            pst.setLong(6, task.getId());
            return pst.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("DB error while updating task", e);
        }
    }

    public boolean delete(long id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(DELETE_SQL)) {

            pst.setLong(1, id);
            return pst.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("DB error while deleting task", e);
        }
    }

    public boolean markCompleted(long id, boolean completed) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(MARK_COMPLETED_SQL)) {

            pst.setInt(1, completed ? 1 : 0);
            pst.setLong(2, id);
            return pst.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("DB error while marking task completed", e);
        }
    }

    private Task mapRow(ResultSet rs) throws SQLException {
        Task task = new Task();
        task.setId(rs.getLong("id"));
        task.setTitle(rs.getString("title"));
        task.setDescription(rs.getString("description"));
        String due = rs.getString("due_date");
        task.setDueDate(due != null ? LocalDate.parse(due) : null);
        task.setCompleted(rs.getInt("completed") == 1);
        task.setCategoryId(rs.getLong("category_id"));
        task.setUserId(rs.getLong("user_id"));
        return task;
    }
}
