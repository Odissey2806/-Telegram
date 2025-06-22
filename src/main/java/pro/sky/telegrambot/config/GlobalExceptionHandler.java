package pro.sky.telegrambot.config;

import liquibase.exception.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pro.sky.telegrambot.exception.*;


@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ReminderParseException.class)
    public void handleParseException(ReminderParseException e) {
        log.warn("Ошибка парсинга: {}", e.getInvalidInput());
    }

    @ExceptionHandler(TelegramApiException.class)
    public void handleTelegramApiException(TelegramApiException e) {
        log.error("Ошибка Telegram API (код {}): {}",
                e.getResponse().errorCode(), e.getMessage());
    }

    @ExceptionHandler(DatabaseException.class)
    public void handleDatabaseException(DatabaseException e) {
        log.error("Ошибка базы данных", e);
    }

    @ExceptionHandler(Exception.class)
    public void handleUnexpectedException(Exception e) {
        log.error("Непредвиденная ошибка", e);
    }
}

