package edu.ssng.ing1.sirius.business.server;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyByte;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import com.fasterxml.jackson.databind.SerializationFeature;

import edu.ssng.ing1.sirius.commons.Notification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito.*;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BroadcastNotificationTest {

    @Test
public void testBroadcast() throws IOException {

    // given
    Set<String> receivers = new HashSet<>();
    receivers.add("receiver1@example.com");
    receivers.add("receiver2@example.com");

    Map<String, Set<String>> connectedStudents = new HashMap<>();
    Set<String> ipAddresses1 = new HashSet<>();
    ipAddresses1.add("127.0.0.1");
    Set<String> ipAddresses2 = new HashSet<>();
    ipAddresses2.add("192.168.0.1");
    connectedStudents.put("receiver1@example.com", ipAddresses1);
    connectedStudents.put("receiver2@example.com", null);
    Notification notification = new Notification();
            notification.setMessage("Votre Amie est connecté");
            notification.setOrder("NEW_CONNECTION");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
            byte[] notifToSend = objectMapper.writeValueAsBytes(notification);


    // when
    try (MockedStatic<ConnectedStudent> mockedStatic = Mockito.mockStatic(ConnectedStudent.class)) {
        mockedStatic.when(ConnectedStudent::getStudentConnectedemailHashmap)
                    .thenReturn(connectedStudents);
        
        try (MockedStatic<Socket> socketMock = Mockito.mockStatic(Socket.class)) {
            Socket socket = Mockito.mock(Socket.class);
            OutputStream outputStream = Mockito.mock(OutputStream.class);
            when(socket.getOutputStream()).thenReturn(outputStream);

            

            

            BroadcastNotification.broadcast("NEW_CONNECTION", receivers, "John");


            // then

            verify(outputStream, times(1)).write(notifToSend); 
            verify(socket, times(1)).close(); 
            
            verify(outputStream, never()).write(any()); 
        }
    }
}



@Test
public void testBroadcastWhenIpReceiversIsEmpty() throws JsonProcessingException {
    
    // given
    Set<String> receivers = Collections.singleton("receiver1@example.com");
    String type = "NEW_CONNECTION";
    HashMap hashMap= new HashMap<>();
    Socket socket = Mockito.mock(Socket.class);


    // when
    try (MockedStatic<ConnectedStudent> mockedStatic = Mockito.mockStatic(ConnectedStudent.class)) {
        mockedStatic.when(ConnectedStudent::getStudentConnectedemailHashmap)
                    .thenReturn(hashMap);

      
        BroadcastNotification.broadcast(type, receivers, "John");

        
        // then
        verifyNoInteractions(socket);
    }
}



@Test
public void testBroadcastWhenIpReceiversIsNull() {
   

    // given
    Set<String> receivers = new HashSet<>();
    receivers.add("receiver1@example.com");
    String type = "NEW_CONNECTION";
    HashMap<String,Set<String>> eHashMap= new HashMap<>();
    eHashMap.put("receiver1@example.com",null);
    Notification notificationService = mock(Notification.class);

    
    // when
    try (MockedStatic<ConnectedStudent> mockedStatic = Mockito.mockStatic(ConnectedStudent.class)) {
        mockedStatic.when(ConnectedStudent::getStudentConnectedemailHashmap)
                    .thenReturn(eHashMap);


        BroadcastNotification.broadcast(type, receivers, "John");

    //    then
        verifyNoInteractions(notificationService);
    } catch (JsonProcessingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
}

  

@Test
public void testBroadcastWhenIpReceiversAreValid() throws JsonProcessingException {

    // given
    Set<String> receivers = new HashSet<>();
    receivers.add("receiver1@example.com");
    String type = "NEW_CONNECTION";
    Set<String> ipReceivers = new HashSet<>();
    ipReceivers.add("127.0.0.1");
    HashMap<String,Set<String>> eHashMap= new HashMap<>();
    eHashMap.put("receiver1@example.com",ipReceivers);
    byte[] byt = new byte[2];
    BroadcastNotification notificationService = mock(BroadcastNotification.class);


    // when

    try (MockedStatic<ConnectedStudent> mockedStatic = Mockito.mockStatic(ConnectedStudent.class)) {
        mockedStatic.when(ConnectedStudent::getStudentConnectedemailHashmap)
                    .thenReturn(eHashMap);

        
        BroadcastNotification.broadcast(type, receivers, "John");

        
        // yhen
        verify(notificationService, times(1)).tryconnection("127.0.0.1", byt, "receiver1@example.com");
    }
}

 @Test
    public void testTryConnectionOnFirstTry() throws Exception {
       
        // given
        String ipReceiver = "127.0.0.1";
        byte[] notifTosend = "test message".getBytes();
        String receiverEmail = "receiver1@example.com";


        // when
        try (MockedConstruction<Socket> mockedSocket = Mockito.mockConstruction(Socket.class,
            (mock, context) -> {
                when(mock.getOutputStream()).thenReturn(mock(OutputStream.class));
            })) {
            
           
            BroadcastNotification.tryconnection(ipReceiver, notifTosend, receiverEmail);

            
            // Then
            Socket socket = mockedSocket.constructed().get(0);
            verify(socket.getOutputStream(), times(1)).write(notifTosend);
            verify(socket, times(1)).close();
        }

        
    }

    @Test
public void testTryConnectionOnSecondTry() throws Exception {
    
    // Given
    String ipReceiver = "127.0.0.1";
    byte[] notifTosend = "test message".getBytes();
    String receiverEmail = "receiver1@example.com";

    try (MockedConstruction<Socket> mockedSocket = Mockito.mockConstruction(Socket.class,
        (mock, context) -> {
            when(mock.getOutputStream()).thenReturn(mock(OutputStream.class));
        })) {
        
        // when
        BroadcastNotification.tryconnection(ipReceiver, notifTosend, receiverEmail);

            // then
        Socket socket = mockedSocket.constructed().get(0);
        verify(socket.getOutputStream(), times(1)).write(notifTosend);
        verify(socket, times(2)).close();
    }
}

@Test
public void testTryConnectionSocketIsNull() throws Exception {
//    given
    String ipReceiver = "127.0.0.1";
    byte[] notifTosend = "test message".getBytes();
    String receiverEmail = "receiver1@example.com";

    try (MockedConstruction<Socket> mockedSocket = Mockito.mockConstruction(Socket.class,
        (mock, context) -> {
            when(mock.getOutputStream()).thenReturn(mock(OutputStream.class));
        })) {

        // when
        BroadcastNotification.tryconnection(ipReceiver, notifTosend, receiverEmail);

        // then
        verify(mockedSocket.constructed().get(0).getOutputStream()).write(notifTosend);
    }
}


    
}
