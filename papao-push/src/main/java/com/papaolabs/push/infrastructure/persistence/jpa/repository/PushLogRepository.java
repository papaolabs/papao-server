package com.papaolabs.push.infrastructure.persistence.jpa.repository;

import com.papaolabs.push.infrastructure.persistence.jpa.entity.PushLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PushLogRepository extends JpaRepository<PushLog, Long> {
    Page<PushLog> findByUserIdOrderByIdDesc(String userId, Pageable pageable);
}
