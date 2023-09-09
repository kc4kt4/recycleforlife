package com.recycleforlife.domain.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.recycleforlife.PostgresTestContainer;
import com.recycleforlife.domain.dto.WorkingHour;
import com.recycleforlife.domain.model.ReceivingPoint;
import com.recycleforlife.domain.model.WorkingHours;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

class ReceivingPointRepositoryTest extends AbstractRepositoryTest {
    @Autowired
    ReceivingPointRepository repository;

    @DynamicPropertySource
    public static void properties(final DynamicPropertyRegistry registry) {
        PostgresTestContainer.properties(registry);
    }

    @BeforeEach
    void setUp() throws Exception {
        super.setUp();
        jdbcTemplate.execute("""
                             delete from "receiving_point";
                             """);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("""
                             delete from "receiving_point";
                             """);
    }

    @Test
    void lifecycle() {
        final ReceivingPoint receivingPoint = new ReceivingPoint()
                .setUuid(UUID.randomUUID())
                .setName("name")
                .setDescription("ddd")
                .setSubtitle("sub")
                .setEmail("e")
                .setMsisdn("8")
                .setLatitude(new BigDecimal("1.2"))
                .setLongitude(new BigDecimal("1.1"))
                .setWorkingHours(
                        new WorkingHours()
                                .setWorkingHours(List.of(
                                        new WorkingHour()
                                                .setFrom(LocalTime.of(8, 10, 10))
                                                .setTo(LocalTime.of(10, 10, 10))
                                                .setDayOfWeek(1)
                                ))
                );

        // test
        final ReceivingPoint saved = repository.save(receivingPoint);

        Assertions.assertThat(saved)
                .isNotNull()
                .matches(e -> e.getId() != null);

        // test
        final Optional<ReceivingPoint> found = repository.findById(saved.getId());

        Assertions.assertThat(found)
                .isPresent()
                .get()
                .isEqualTo(saved);
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
