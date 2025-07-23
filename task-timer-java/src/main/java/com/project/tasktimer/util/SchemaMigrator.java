package com.project.tasktimer.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Executes the schema.sql script on application start‑up.
 * Call SchemaMigrator.run("jdbc:sqlite:tasktimer.db") once in your main entry point.
 */
public final class SchemaMigrator {

    private static final String SCHEMA_PATH = "/schema.sql";

    private SchemaMigrator() {
        // utility class
    }

    public static void run(String jdbcUrl) {
        try (Connection conn = DriverManager.getConnection(jdbcUrl)) {
            executeSql(conn, readSchema());
            System.out.println("✓ Database schema is up to date.");
        } catch (SQLException | IOException e) {
            throw new RuntimeException("Failed to apply schema migration", e);
        }
    }

    private static String readSchema() throws IOException {
        StringBuilder sb = new StringBuilder();
        try (InputStream in = SchemaMigrator.class.getResourceAsStream(SCHEMA_PATH);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        }
        return sb.toString();
    }

    private static void executeSql(Connection conn, String sql) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("BEGIN;");
            stmt.executeUpdate(sql);
            stmt.executeUpdate("COMMIT;");
        }
    }
}
