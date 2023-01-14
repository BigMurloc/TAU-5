package org.example.race_result;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RaceResultServiceMockitoTest {

    private final RaceResultService service = new RaceResultService();

    @Mock
    private Client subscriber;

    @Test
    public void shouldAddSubscriber() {
        //given
        service.addSubscriber(subscriber);
        Message message = mock(Message.class);

        //when
        service.send(message);

        //then
        verify(subscriber, times(1)).receive(message);
    }

    @Test
    public void shouldSendMessageToMultipleClients() {
        //given
        Client subscriber2 = mock(Client.class);
        Client notAddedClient = mock(Client.class);
        service.addSubscriber(subscriber);
        service.addSubscriber(subscriber2);
        Message message = mock(Message.class);

        //when
        service.send(message);

        //then
        verify(subscriber, times(1)).receive(message);
        verify(subscriber2, times(1)).receive(message);
        verify(notAddedClient, never()).receive(message);
    }

    @Test
    public void shouldRemoveSubscriber() {
        //when
        service.addSubscriber(subscriber);
        service.removeSubscriber(subscriber);
        service.send(mock(Message.class));

        //then
        verify(subscriber, never()).receive(any(Message.class));
    }

}
