package com.papaolabs.api.infrastructure.persistence.jpa.repository;

import com.papaolabs.api.infrastructure.persistence.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUid(String uid);
}
