
package pro.sky.telegrambot.exception;

import com.pengrad.telegrambot.response.SendResponse;

public class TelegramApiException extends TelegramBotException {
    private final SendResponse response;

    public TelegramApiException(SendResponse response) {
        super("Ошибка Telegram API: " + response.description());
        this.response = response;
    }

    public SendResponse getResponse() {
        return response;
    }
}
