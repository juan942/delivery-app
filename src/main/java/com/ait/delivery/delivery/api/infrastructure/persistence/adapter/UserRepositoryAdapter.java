package com.ait.delivery.delivery.api.infrastructure.persistence.adapter;

import com.ait.delivery.delivery.api.domain.model.User;
import com.ait.delivery.delivery.api.domain.port.out.UserRepository;
import com.ait.delivery.delivery.api.infrastructure.persistence.mapper.UserMapper;
import com.ait.delivery.delivery.api.infrastructure.persistence.repository.UserJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository jpa;

    public UserRepositoryAdapter(UserJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Optional<User> findByUserName(String username) {
        return jpa.findByUsername(username).map(UserMapper::entityToDomain);
    }
}
