package com.recycleforlife.domain.mapper;

import com.recycleforlife.domain.dto.UserDto;
import com.recycleforlife.domain.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserDto toDto(User user);
}
