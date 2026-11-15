package com.ait.delivery.delivery.api.infrastructure.persistence.mapper;

import com.ait.delivery.delivery.api.domain.model.User;
import com.ait.delivery.delivery.api.infrastructure.persistence.entity.UserEntity;

public class UserMapper {
    private UserMapper() {}

    public static User entityToDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getUsername(),
                entity.getPassword()
        );
    }
}
