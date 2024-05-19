package edu.ssng.ing1.sirius.client.notificationManagement;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import org.junit.Before;
import org.junit.Test;

import edu.ssng.ing1.sirius.commons.Notification;

public class NotifyHandlerTest {

    private Socket socket;
    private InputStream inputStream;
    private NotifyHandler notifyHandler;

    @Before
    public void setUp() {
        socket = mock(Socket.class);
        inputStream = mock(InputStream.class);
        notifyHandler = new NotifyHandler(socket);
    }

    @Test
    public void testGetToNotify() throws IOException {
     
        byte[] testData = "{\"message\": \"Test notification\"}".getBytes(); 
        Notification expectedNotification = new Notification();
        expectedNotification.setMessage("Test notification");

        
        when(socket.getInputStream()).thenReturn(inputStream);
        when(inputStream.read(any(byte[].class))).thenReturn(testData.length);
        when(inputStream.read(any(byte[].class), anyInt(), anyInt())).thenReturn(testData.length);
        when(inputStream.read(any(byte[].class))).thenReturn(testData.length);
        when(socket.isClosed()).thenReturn(false);

       
        Notification result = notifyHandler.getToNotify(testData);

    
        assertEquals(expectedNotification.getMessage(), result.getMessage());
        
    }
}

