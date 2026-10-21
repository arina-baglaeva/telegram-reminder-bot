package ru.skypro.telegram_bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.skypro.telegram_bot.model.NotificationTask;
import ru.skypro.telegram_bot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class NotificationScheduler {

    private static final Logger logger = LoggerFactory.getLogger(NotificationScheduler.class);

    private final NotificationTaskRepository repository;
    private final TelegramBot bot;

    public NotificationScheduler(NotificationTaskRepository repository, @Value("${telegram.bot.token}") String botToken) {
        this.repository = repository;
        this.bot = new TelegramBot(botToken);
    }


    @Scheduled(cron = "0 * * * * *")
    public void sendNotifications() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        logger.info("Шедулер запущен. Ищем задачи на время: {}", now);

        List<NotificationTask> tasks = repository.findByDateTime(now);

        for (NotificationTask task : tasks) {
            SendMessage message = new SendMessage(task.getChatId(), task.getMessage());
            bot.execute(message);

            logger.info("Отправлено напоминание в чат {}: {}", task.getChatId(), task.getMessage());
        }
    }
}