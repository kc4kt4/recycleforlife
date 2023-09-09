package com.recycleforlife.domain.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.recycleforlife.PostgresTestContainer;
import com.recycleforlife.domain.model.Fraction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.List;
import java.util.UUID;

class FractionRepositoryTest extends AbstractRepositoryTest {
    @Autowired
    FractionRepository repository;

    @DynamicPropertySource
    public static void properties(final DynamicPropertyRegistry registry) {
        PostgresTestContainer.properties(registry);
    }

    @Test
    void findForCategory() {
        jdbcTemplate.execute(
                """
                insert into category (uuid, "name", description)
                values ('d89bef63-3032-4934-9ca9-40ec1837a3cb', 'c1', 'd1');
                insert into fraction (uuid, category_id, "name", description, article, image_base64, title)
                values ('fbaa0cda-8a82-41b9-8113-c2ea7f33ce8d', (select id from category where uuid = 'd89bef63-3032-4934-9ca9-40ec1837a3cb'), 'f1', 'fd1', 'a1', 'i1', 't1'),
                       ('5d9e58b7-5a99-4087-8265-7f82c33a4417', (select id from category where uuid = 'd89bef63-3032-4934-9ca9-40ec1837a3cb'), 'f2', 'fd2', 'a2', 'i2', 't2');
                """
        );

        // test
        final List<Fraction> result = repository.findForCategory(UUID.fromString("d89bef63-3032-4934-9ca9-40ec1837a3cb"));

        Assertions.assertThat(result)
                .hasSize(2)
                .map(Fraction::getUuid)
                .map(UUID::toString)
                .containsExactly("fbaa0cda-8a82-41b9-8113-c2ea7f33ce8d", "5d9e58b7-5a99-4087-8265-7f82c33a4417");
    }

    @TestConfiguration
    static class Config {

        @Bean
        ObjectMapper objectMapper() {
            return new ObjectMapper()
                    .registerModule(new JavaTimeModule());
        }
    }
}
