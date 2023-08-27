package com.recycleforlife;

import org.jetbrains.annotations.NotNull;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.delegate.DatabaseDelegate;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

import java.util.Collections;

public class PostgresTestContainer {
    public static final PostgresSQLContainerWithFixedPortPossibility POSTGRES_CONTAINER = new PostgresSQLContainerWithFixedPortPossibility(
            DockerImageName.parse("postgres:latest"));

    static {
        Startables.deepStart(Collections.singletonList(POSTGRES_CONTAINER)).join();
    }

    public static void clearDatabase() {
        ScriptUtils.runInitScript(POSTGRES_CONTAINER.getDatabaseDelegate(), "init-postgres-for-test.sql");
    }

    public static void properties(final @NotNull DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);
        registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
    }

    public static class PostgresSQLContainerWithFixedPortPossibility extends PostgreSQLContainer {
        public PostgresSQLContainerWithFixedPortPossibility(DockerImageName dockerImageName) {
            super(dockerImageName);
        }

        public PostgresSQLContainerWithFixedPortPossibility fixContainerPort(int containerPort) {
            super.addFixedExposedPort(5432, containerPort);
            return this;
        }

        @Override
        public DatabaseDelegate getDatabaseDelegate() {
            return super.getDatabaseDelegate();
        }
    }
}
