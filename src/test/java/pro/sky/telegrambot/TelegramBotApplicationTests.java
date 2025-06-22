
package pro.sky.telegrambot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // Активируем тестовый профиль
class TelegramBotApplicationTests {

	@Test
	void contextLoads() {
		// Тест проверяет только загрузку контекста Spring
	}
}