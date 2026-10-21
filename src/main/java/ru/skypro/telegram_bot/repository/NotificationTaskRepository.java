package ru.skypro.telegram_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.telegram_bot.model.NotificationTask;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationTaskRepository extends JpaRepository<NotificationTask, Long> {
    List<NotificationTask> findByDateTime(LocalDateTime dateTime);
}
