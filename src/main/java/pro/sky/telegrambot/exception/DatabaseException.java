
package pro.sky.telegrambot.exception;

import org.springframework.dao.DataAccessException;

public class DatabaseException extends TelegramBotException {
    public DatabaseException(DataAccessException cause) {
        super("Ошибка базы данных: " + cause.getMessage(), cause);
    }
}
