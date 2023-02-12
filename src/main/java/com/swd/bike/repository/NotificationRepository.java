package com.swd.bike.repository;

import com.swd.bike.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long>, JpaSpecificationExecutor<Notification> {
    List<Notification> findAllByAccountIdAndIsReadIsFalse(String accountId);

    Long countByAccountIdAndIsReadIsFalse(String accountId);
}