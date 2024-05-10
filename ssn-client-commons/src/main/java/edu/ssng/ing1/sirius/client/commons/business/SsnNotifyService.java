package edu.ssng.ing1.sirius.client.commons.business;

import java.io.IOException;
import java.net.Socket;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ssng.ing1.sirius.commons.Notification;

public class SsnNotifyService {

    ObjectMapper objectMapper = new ObjectMapper();

    public void dispatch(Notification Notify)
            throws JsonParseException, JsonMappingException, IOException {

        switch (Notify.getOrder()) {

            case "NEW_CONNECTION":
                notifyConnection(Notify);

            case "CONNECT_USER":
                connectUser(Notify);

            default:
                break;
        }
    }

    public void notifyConnection(Notification notification){

        System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
                System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
                System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
                System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
                System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
                System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
                
        
    }
    public void connectUser(Notification notification){

    }
    
}
