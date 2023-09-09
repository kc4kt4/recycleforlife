package com.recycleforlife;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

// @ActiveProfiles({"test", "docker"})
@ActiveProfiles({"test"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AppTest {

    @DynamicPropertySource
    public static void properties(final DynamicPropertyRegistry registry) {
        PostgresTestContainer.properties(registry);
    }

    @Test
    void context() {
        // System.out.println(PostgresTestContainer.POSTGRES_CONTAINER.getJdbcUrl());
        // System.out.println(PostgresTestContainer.POSTGRES_CONTAINER.getUsername());
        // System.out.println(PostgresTestContainer.POSTGRES_CONTAINER.getPassword());
        // System.out.println("");

        // while (true) {}
    }
}
