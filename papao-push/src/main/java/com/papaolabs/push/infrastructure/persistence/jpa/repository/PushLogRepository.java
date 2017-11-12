package com.papaolabs.push.infrastructure.persistence.jpa.repository;

import com.papaolabs.push.infrastructure.persistence.jpa.entity.PushLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PushLogRepository extends JpaRepository<PushLog, Long> {
    List<PushLog> findByUserId(Long userId);
    Long deleteByUserIdAndPushId(Long userId, Long pushId);
}
