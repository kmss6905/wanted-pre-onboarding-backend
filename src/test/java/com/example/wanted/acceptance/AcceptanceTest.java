package com.example.wanted.acceptance;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
public abstract class AcceptanceTest {

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8")
        .withDatabaseName("wanted");

    @AfterEach
    void tearDown() {
        // TRUNCATE TABLE
        databaseCleaner.execute();
    }
}
