package com.recycleforlife.domain.repository;

import com.recycleforlife.PostgresTestContainer;
import liquibase.integration.spring.SpringLiquibase;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;

@ActiveProfiles("test")
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(
        properties = {
                "logging.level.org.springframework.data=TRACE"
        }
)
class AbstractRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    SpringLiquibase springLiquibase;

    @DynamicPropertySource
    public static void properties(final DynamicPropertyRegistry registry) {
        PostgresTestContainer.properties(registry);
    }

    @BeforeEach
    void setUp() throws Exception {
        PostgresTestContainer.clearDatabase();
        springLiquibase.afterPropertiesSet();
    }
}
