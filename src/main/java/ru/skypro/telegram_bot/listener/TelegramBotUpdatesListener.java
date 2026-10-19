package ru.skypro.telegram_bot.listener;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TelegramBotUpdatesListener implements UpdatesListener {

    private static final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot bot;

    public TelegramBotUpdatesListener(@Value("${telegram.bot.token}") String botToken) {
        this.bot = new TelegramBot(botToken);
    }

    @PostConstruct
    public void init() {
        bot.setUpdatesListener(this);
        logger.info("Telegram Bot успешно запущен и готов слушать сообщения!");
    }

    @Override
    public int process(List<Update> updates) {
        try {
            for (Update update : updates) {
                if (update.message() != null && update.message().text() != null) {

                    String text = update.message().text();
                    Long chatId = update.message().chat().id();

                    logger.info("Получено сообщение '{}' от чата {}", text, chatId);

                    if ("/start".equals(text)) {
                        SendMessage message = new SendMessage(chatId, "Привет! Я бот-напоминалка. Напиши мне задачу в формате: 01.01.2022 20:00 Сделать домашнюю работу");
                        bot.execute(message);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Ошибка при обработке входящего сообщения", e);
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}