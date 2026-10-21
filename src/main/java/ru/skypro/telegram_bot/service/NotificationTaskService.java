package ru.skypro.telegram_bot.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.telegram_bot.model.NotificationTask;
import ru.skypro.telegram_bot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NotificationTaskService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationTaskService.class);

    private static final Pattern MESSAGE_PATTERN = Pattern.compile("(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})(\\s+)(.+)");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private final NotificationTaskRepository repository;

    public NotificationTaskService(NotificationTaskRepository repository) {
        this.repository = repository;
    }

    public boolean processMessage(Long chatId, String text) {
        Matcher matcher = MESSAGE_PATTERN.matcher(text);

        if (matcher.matches()) {
            try {
                String dateString = matcher.group(1);
                String messageText = matcher.group(3);
                LocalDateTime dateTime = LocalDateTime.parse(dateString, DATE_TIME_FORMATTER);

                NotificationTask task = new NotificationTask(chatId, messageText, dateTime);
                repository.save(task);

                logger.info("Успешно сохранена задача: '{}' на время {}", messageText, dateTime);
                return true;
            } catch (Exception e) {
                logger.error("Ошибка при парсинге сообщения", e);
                return false;
            }
        } else {
            logger.info("Сообщение не соответствует формату: {}", text);
            return false;
        }
    }
}