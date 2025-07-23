package com.project.tasktimer.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {

    private static final String JDBC_URL = "jdbc:sqlite:src/main/resources/task_timer.db";
    private static Connection connection;

    private DBConnection() {
        /* utility class – no instances */ 
    }

    /**
     * Returns a live {@link Connection}. Creates one if none exists or if it was closed.
     *
     * @throws SQLException if the connection cannot be opened
     */
    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(JDBC_URL);
        }
        return connection;
    }

    /**
     * Closes and nulls the singleton connection.
     */
    public static synchronized void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Failed to close DB connection: " + e.getMessage());
            } finally {
                connection = null;
            }
        }
    }
}
