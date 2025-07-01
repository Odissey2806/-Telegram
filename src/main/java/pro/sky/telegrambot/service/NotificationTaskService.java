
package pro.sky.telegrambot.service;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class NotificationTaskService {


    private final NotificationTaskRepository repository;
    private final TelegramBot telegramBot;
    private final Pattern pattern = Pattern.compile("(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})\\s+(.+)");
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public NotificationTaskService(NotificationTaskRepository repository,
                                   TelegramBot telegramBot) {
        this.repository = repository;
        this.telegramBot = telegramBot;
    }


    @Transactional
    public void processMessage(Long chatId, String text) {
        try {
            Matcher matcher = pattern.matcher(text);
            if (!matcher.matches()) {
                sendBotMessage(chatId, "⚠️ Неверный формат. Пример: 31.12.2023 18:00 Поздравить с НГ");
                return;
            }


            LocalDateTime dateTime = LocalDateTime.parse(matcher.group(1), formatter);
            NotificationTask task = new NotificationTask();
            task.setChatId(chatId);
            task.setMessage(matcher.group(2));
            task.setReminderTime(dateTime);

            repository.save(task);
            sendBotMessage(chatId, "✅ Напоминание создано: " + matcher.group(2));

        } catch (Exception e) {
            sendBotMessage(chatId, "❌ Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void checkReminders() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        List<NotificationTask> tasks = repository.findByReminderTimeAndIsSentFalse(now);

        tasks.forEach(task -> {
            sendBotMessage(task.getChatId(), "🔔 Напоминание: " + task.getMessage());
            task.setSent(true);
            repository.save(task);
        });
    }

    private void sendBotMessage(Long chatId, String text) {
        telegramBot.execute(new SendMessage(chatId, text));
    }
}