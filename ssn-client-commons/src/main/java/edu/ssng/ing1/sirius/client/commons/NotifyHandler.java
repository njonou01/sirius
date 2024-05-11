package edu.ssng.ing1.sirius.client.commons;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ssng.commons.LoggingUtils;
import edu.ssng.ing1.sirius.client.commons.business.SsnNotifyService;
import edu.ssng.ing1.sirius.commons.Notification;

public class NotifyHandler extends Thread{

    private Socket socket=new Socket();
    private InputStream inputStream;
  
    final static String LoggingLabel = "N o t i f y -  h a n d l e r";

    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);


    public NotifyHandler(Socket socket){
        this.socket=socket;

    }
   

    @Override
    public void run() {
        try {
            inputStream = this.socket.getInputStream();
            byte[] buffer = new byte[1024];
            int br = inputStream.read(buffer);
            Notification notification = getToNotify(buffer);
            SsnNotifyService xmartNotificationService = new SsnNotifyService();
            xmartNotificationService.dispatch(notification);
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                this.socket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
    }

    private final Notification getToNotify(byte[] data) throws IOException {
        logger.debug("data received {} bytes", data.length);
        LoggingUtils.logDataMultiLine(logger, Level.DEBUG, data);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
        final Notification Notify = mapper.readValue(data, Notification.class);
        logger.debug(Notify.toString());
        return Notify;
    }
    
}
