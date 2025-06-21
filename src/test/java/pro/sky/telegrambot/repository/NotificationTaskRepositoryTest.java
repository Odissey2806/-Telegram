
package pro.sky.telegrambot.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pro.sky.telegrambot.model.NotificationTask;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NotificationTaskRepositoryTest {

    @Autowired
    private NotificationTaskRepository repository;

    @Test
    void findByReminderTimeAndIsSentFalse_ShouldFindTasks() {
        // Подготовка
        LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        NotificationTask task = new NotificationTask();
        task.setChatId(123L);
        task.setMessage("Тест");
        task.setReminderTime(time);
        repository.save(task);

        // Выполнение
        List<NotificationTask> found = repository.findByReminderTimeAndIsSentFalse(time);

        // Проверка
        assertEquals(1, found.size());
        assertEquals("Тест", found.get(0).getMessage());
    }
}
