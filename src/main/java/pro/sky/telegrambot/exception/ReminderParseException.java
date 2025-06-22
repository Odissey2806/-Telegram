
package pro.sky.telegrambot.exception;


import java.time.format.DateTimeParseException;


public class ReminderParseException extends TelegramBotException {
    private final String invalidInput;


    public ReminderParseException(String invalidInput, DateTimeParseException cause) {
        super("Некорректный формат даты: " + invalidInput +
                ". Требуемый формат: ДД.ММ.ГГГГ ЧЧ:MM", cause);
        this.invalidInput = invalidInput;
    }


    public String getInvalidInput() {
        return invalidInput;
    }
}
