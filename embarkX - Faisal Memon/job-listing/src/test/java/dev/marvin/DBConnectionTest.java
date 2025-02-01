package dev.marvin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionTest extends TestContainersTest {

    @Test
    void testContainerIsRunning() {
        Assertions.assertTrue(postgreSQLContainer.isRunning(), "PostgreSQL TestContainer should be running");
    }

    @Test
    void testDatabaseConnection() throws SQLException {
        String jdbcUrl = postgreSQLContainer.getJdbcUrl();
        String username = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            Assertions.assertNotNull(connection, "Connection should not be null");
            Assertions.assertTrue(connection.isValid(2), "Database connection should be valid");
        }
    }
}
