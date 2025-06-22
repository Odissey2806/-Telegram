package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.service.NotificationTaskService;
import jakarta.annotation.PostConstruct;
import java.util.List;

@Component
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final TelegramBot telegramBot;
    private final NotificationTaskService taskService;

    public TelegramBotUpdatesListener(TelegramBot telegramBot,
                                      NotificationTaskService taskService) {
        this.telegramBot = telegramBot;
        this.taskService = taskService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            if (update.message() != null && update.message().text() != null) {
                handleMessage(update.message().chat().id(), update.message().text());
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;  // Уточнен константный доступ
    }

    private void handleMessage(Long chatId, String text) {
        if ("/start".equals(text)) {
            sendMessage(chatId, "Привет! Я бот для напоминаний. Формат: ДД.ММ.ГГГГ ЧЧ:MM Текст");
        } else {
            taskService.processMessage(chatId, text);
        }
    }

    private void sendMessage(Long chatId, String text) {
        telegramBot.execute(new SendMessage(chatId, text));
    }
}