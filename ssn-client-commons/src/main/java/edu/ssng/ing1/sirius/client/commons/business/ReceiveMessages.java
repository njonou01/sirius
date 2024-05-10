package edu.ssng.ing1.sirius.client.commons.business;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ssng.ing1.sirius.commons.Notification;

public class ReceiveMessages implements Runnable {
    private InputStream inputStream;
    private Socket socket;
    private ObjectMapper objectMapper= new ObjectMapper();

    public ReceiveMessages(InputStream inputStream, Socket socket) {
        this.inputStream = inputStream;
        this.socket = socket;
        ObjectMapper objectMapper = new ObjectMapper();
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        try {
            System.out.println(socket);
            System.out.println("JJJJJJJJJJJJJJJJJJJJJJJJJJJJJ");
            System.out.println("JJJJJJJJJJJJJJJJJJJJJJJJJ");
            System.out.println("JJJJJJJJJJJJJJJJJJJJJJJJJJJJ");
            System.out.println("JJJJJJJJJJJJJJJJJJJJJJJ");
            System.out.println("JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ");

            byte[] buffer = new byte[1024];
            int bytesRead;

            // String str = new String(buffer, StandardCharsets.UTF_8);
            // System.out.println(str);
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                // String toDisplay = objectMapper.readValue(buffer, String.class);
                Notification notification = objectMapper.readValue(buffer, Notification.class);

                System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
                System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
                System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
                System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
                System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");

                // System.out.println(toDisplay);
                System.out.println(notification);

            }
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
