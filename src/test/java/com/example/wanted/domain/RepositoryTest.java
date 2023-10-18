package com.example.wanted.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(value = H2DatabaseCleaner.class)
public class RepositoryTest {

    @Autowired
    private H2DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        databaseCleaner.afterPropertiesSet();
    }

    @AfterEach
    void tearDown() {
        databaseCleaner.execute();
    }

}
