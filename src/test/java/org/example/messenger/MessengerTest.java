package org.example.messenger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MessengerTest {
    @Mock
    private MailServer mailServer;
    @Mock
    private TemplateEngine templateEngine;
    @Mock
    private Client client;
    @Mock
    private Template template;

    @Test
    public void shouldSendMessage() {
        // given
        String email = "test@test.com";
        String msgContent = "Test message";
        Messenger messenger = new Messenger(mailServer, templateEngine);
        when(client.getEmail()).thenReturn(email);
        when(templateEngine.prepareMessage(template, client)).thenReturn(msgContent);

        //when
        messenger.sendMessage(client, template);

        //then
        verify(mailServer).send(email, msgContent);
    }

    @Test
    public void shouldNotSendEmailWhenEmailIsInvalid() {
        //given
        Messenger messenger = new Messenger(mailServer, templateEngine);
        when(client.getEmail()).thenReturn(null);

        //when
        messenger.sendMessage(client, template);

        //then
        verify(mailServer, never()).send(anyString(), anyString());
    }

    @Test
    public void shouldNotSendEmptyTemplate() {
        // given
        when(templateEngine.prepareMessage(template, client)).thenReturn("");
        Messenger messenger = new Messenger(mailServer, templateEngine);

        //when
        messenger.sendMessage(client, template);

        //then
        verify(mailServer, never()).send(anyString(), anyString());
    }

    @Test
    public void shouldNotSendMessageWithNullClient() {
        //given
        Messenger messenger = new Messenger(mailServer, templateEngine);

        //then
        assertThrows(IllegalArgumentException.class, () -> messenger.sendMessage(null, template), "Client should not be null");
    }

    @Test
    public void shouldNotSendMessageWithNullTemplate() {
        //given
        Messenger messenger = new Messenger(mailServer, templateEngine);

        //then
        assertThrows(IllegalArgumentException.class, () -> messenger.sendMessage(client, null), "T");
    }

}
