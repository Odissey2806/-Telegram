
package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.service.NotificationTaskService;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Chat;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TelegramBotUpdatesListenerTest {

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private NotificationTaskService taskService;

    @InjectMocks
    private TelegramBotUpdatesListener listener;

    @Test
    void processUpdate_StartCommand_SendsWelcomeMessage() {
        // Подготовка
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);

        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/start");
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(123L);

        // Выполнение
        listener.process(List.of(update));

        // Проверка
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }

    @Test
    void processUpdate_ValidReminder_CallsService() {
        // Подготовка
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);

        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("31.12.2023 23:59 Тест");
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(123L);

        // Выполнение
        listener.process(List.of(update));

        // Проверка
        verify(taskService, times(1)).processMessage(eq(123L), anyString());
    }
}