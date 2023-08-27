package com.recycleforlife.domain.repository;

import com.recycleforlife.PostgresTestContainer;
import com.recycleforlife.domain.model.News;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

class NewsRepositoryTest extends AbstractRepositoryTest {
    @Autowired
    NewsRepository repository;

    @DynamicPropertySource
    public static void properties(final DynamicPropertyRegistry registry) {
        PostgresTestContainer.properties(registry);
    }

    @BeforeEach
    void setUp() throws Exception {
        super.setUp();
        jdbcTemplate.execute("""
                             delete from "news";
                             """);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("""
                             delete from "news";
                             """);
    }

    @Test
    void findByUuid() {
        jdbcTemplate.execute(
                """
                insert into news(id, uuid, "date", short_description, description, name, title)
                values (1, '7efe7e67-031a-4a9e-a89c-c448a867d248', '2020-10-10', 'sd1', 'd1', 'n1', '1'),
                       (2, '24ceafbd-ee80-4f10-8807-77f1932dafac', '2020-10-13', 'sd2', 'd2', 'n2', '2');
                """
        );

        // test
        final Optional<News> result = repository.findByUuid(UUID.fromString("7efe7e67-031a-4a9e-a89c-c448a867d248"));

        Assertions.assertThat(result)
                .isPresent()
                .get()
                .matches(e -> e.getId() == 1L);
    }

    @Test
    void findByPeriod_case1() {
        jdbcTemplate.execute(
                """
                insert into news(id, uuid, "date", short_description, description, name, title)
                values (1, '7efe7e67-031a-4a9e-a89c-c448a867d248', '2020-10-10', 'sd1', 'd1', 'n1', '1'),
                       (2, '24ceafbd-ee80-4f10-8807-77f1932dafac', '2020-10-13', 'sd2', 'd2', 'n2', '2');
                """
        );

        // test
        final List<News> result = repository.findByPeriod(LocalDate.of(2020, 10, 10), LocalDate.of(2020, 10, 12));

        Assertions.assertThat(result)
                .hasSize(1)
                .first()
                .matches(e -> e.getId() == 1L);
    }

    @Test
    void findByPeriod_case2() {
        jdbcTemplate.execute(
                """
                insert into news(id, uuid, "date", short_description, description, name, title)
                values (1, '7efe7e67-031a-4a9e-a89c-c448a867d248', '2020-10-10', 'sd1', 'd1', 'n1', '1'),
                       (2, '24ceafbd-ee80-4f10-8807-77f1932dafac', '2020-10-13', 'sd2', 'd2', 'n2', '2');
                """
        );

        // test
        final List<News> result = repository.findByPeriod(LocalDate.of(2020, 10, 10), LocalDate.of(2020, 10, 15));

        Assertions.assertThat(result)
                .hasSize(2)
                .map(News::getId)
                .containsExactly(1L, 2L);
    }
}
