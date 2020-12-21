package com.demo.ing.repository;

import com.demo.ing.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByName(String name);

    UserEntity findBySsn(String name);
}
