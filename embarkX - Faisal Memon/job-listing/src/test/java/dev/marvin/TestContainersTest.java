package dev.marvin;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Profile("test")
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
public abstract class TestContainersTest {

    private static final Logger log = LoggerFactory.getLogger(TestContainersTest.class);

    @Container
    protected static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test_db")
            .withUsername("root")
            .withPassword("password")
            .withExposedPorts(5342);

    @BeforeAll
    static void startContainer() {
        postgreSQLContainer.start();
        log.info("🚀 Testcontainer started on: {}", postgreSQLContainer.getJdbcUrl());
    }

    @DynamicPropertySource
    static void registerDynamicDBProps(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Test
    void testContainerIsRunning() {
        Assertions.assertTrue(postgreSQLContainer.isRunning());
    }

    @Test
    void testDatabaseConnection() throws SQLException {
        String jdbcUrl = postgreSQLContainer.getJdbcUrl();
        String username = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            Assertions.assertNotNull(connection);
            Assertions.assertTrue(connection.isValid(2));
        }
    }
}
