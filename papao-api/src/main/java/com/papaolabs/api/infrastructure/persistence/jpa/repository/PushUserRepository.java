package com.papaolabs.api.infrastructure.persistence.jpa.repository;

import com.papaolabs.api.infrastructure.persistence.jpa.entity.PushUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PushUserRepository extends JpaRepository<PushUser, Long> {
    List<PushUser> findByUserId(Long userId);

    PushUser findByDeviceId(String deviceId);
}
