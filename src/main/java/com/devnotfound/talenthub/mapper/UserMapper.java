package com.devnotfound.talenthub.mapper;

import com.devnotfound.talenthub.dto.UserRequestDTO;
import com.devnotfound.talenthub.dto.UserResponseDTO;
import com.devnotfound.talenthub.entity.User;

public class UserMapper {

    public static User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        return user;
    }

    public static UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getCreationDate()
        );
    }
}
