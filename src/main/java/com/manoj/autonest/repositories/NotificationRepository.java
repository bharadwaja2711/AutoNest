package com.manoj.autonest.repositories;

import com.manoj.autonest.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
