# Telegram Reminder Bot

Телеграм-бот для создания и получения напоминаний.

## Описание проекта

Бот принимает сообщения от пользователя в формате `01.01.2022 20:00 Сделать домашнюю работу`, парсит из них дату и текст, сохраняет задачу в базу данных PostgreSQL и отправляет уведомление в указанное время.

## Стек технологий

- **Java 17+**
- **Spring Boot** (Web, Data JPA, Scheduling)
- **PostgreSQL** (база данных)
- **Liquibase** (миграции базы данных)
- **Telegram Bot API** (библиотека `java-telegram-bot-api`)
- **Maven** (сборка проекта)

## Как запустить проект локально

1. Клонируйте репозиторий:

   ```bash
   git clone https://github.com/arina-baglaeva/telegram-reminder-bot.git
   ```

2. Создайте базу данных `telegram_bot` в PostgreSQL.

3. В папке `src/main/resources` создайте файл `application-local.properties` и укажите ваши локальные данные:

   ```properties
   spring.datasource.password=ваш_пароль_от_базы
   telegram.bot.token=ваш_токен_от_BotFather
   ```

4. Запустите приложение через класс `TelegramBotApplication` или командой:

   ```bash
   mvn spring-boot:run
   ```

## Использование

1. Найдите бота в Telegram.
2. Отправьте команду `/start` для приветствия.
3. Отправьте задачу строго в формате: `дд.мм.гггг чч:мм Текст напоминания`.
4. Дождитесь уведомления в указанное время!