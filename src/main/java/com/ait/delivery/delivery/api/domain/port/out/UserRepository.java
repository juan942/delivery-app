package com.ait.delivery.delivery.api.domain.port.out;

import com.ait.delivery.delivery.api.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUserName(String username);
}
