
package pro.sky.telegrambot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class TelegramBotConfigurationTest {

    @Autowired
    private TelegramBot telegramBot;

    @Test
    void telegramBotBean_ShouldBeCreated() {
        assertNotNull(telegramBot);
    }
}
