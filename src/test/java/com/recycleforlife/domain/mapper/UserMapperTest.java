package com.recycleforlife.domain.mapper;

import com.recycleforlife.domain.dto.Sex;
import com.recycleforlife.domain.dto.UserDto;
import com.recycleforlife.domain.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.UUID;

class UserMapperTest {
    final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    void toDto() {
        final User user = new User()
                .setSex(Sex.MALE)
                .setUuid(UUID.randomUUID())
                .setName("b")
                .setDateOfBirth(LocalDate.of(2020, 10, 10))
                .setLogin("g")
                .setId(122L)
                .setEncodedPassword("aa");

        // test
        final UserDto result = mapper.toDto(user);

        Assertions.assertThat(result)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(user);
    }
}
