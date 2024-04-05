package edu.ssng.ing1.sirius.client.commons;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ssng.commons.LoggingUtils;
import edu.ssng.ing1.sirius.commons.Request;
import edu.ssng.ing1.sirius.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public abstract class ClientRequest<N, S> implements Runnable {
    private final Thread self;
    private final NetworkConfig networkConfig;
    private static final String threadNamePrfx = "client_request";
    private final byte[] bytes;
    private static final int maxTimeLapToGetAClientReplyInMs = 60000;
    private static final int timeStepMs = 300;
    private final String threadName;
    private final static String LoggingLabel = "C l i e n t - R e q u e s t";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final BlockingDeque<Integer> waitArtifact = new LinkedBlockingDeque<Integer>(1);
    private final N info;
    protected S result;

    public ClientRequest(final NetworkConfig networkConfig,
            final int myBirthDate,
            final Request request,
            final N info,
            final byte[] bytes) throws IOException {
        this.networkConfig = networkConfig;
        final StringBuffer threadNameBuffer = new StringBuffer();
        threadNameBuffer.append(threadNamePrfx).append("★").append(String.format("%04d", myBirthDate));
        threadName = threadNameBuffer.toString();
        this.bytes = bytes;
        this.info = info;
        self = new Thread(this, threadNameBuffer.toString());
        self.start();

    }

    @Override
    public void run() {
        try (Socket socket = new Socket()) {
            int bufferSize = 20 * 1024 * 1024; // 200 Mo en octets
            socket.setReceiveBufferSize(bufferSize);
            logger.debug("Taille du buffer définie à : {} octets pour le client", bufferSize);
            socket.connect(new InetSocketAddress(networkConfig.getIpaddress(), networkConfig.getTcpport()));
            logger.debug("Connecté au serveur {} sur le port {}", networkConfig.getIpaddress(), networkConfig.getTcpport());
    
            try (InputStream instream = socket.getInputStream();
                 OutputStream outstream = socket.getOutputStream()) {
    
                LoggingUtils.logDataMultiLine(logger, Level.DEBUG, bytes);
                outstream.write(bytes);
    
                int timeout = maxTimeLapToGetAClientReplyInMs;
                while (0 == instream.available() && timeout > 0) {
                    waitArtifact.pollFirst(timeStepMs, TimeUnit.MILLISECONDS);
                    timeout -= timeStepMs;
                }
                if (timeout <= 0) {
                    logger.warn("Le délai d'attente pour la réponse du serveur est écoulé.");
                    return;
                }
    
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = instream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                byte[] inputData = outputStream.toByteArray();
                logger.trace("Octets lus = {}", inputData.length);
                LoggingUtils.logDataMultiLine(logger, Level.TRACE, inputData);
    
                final ObjectMapper mapper = new ObjectMapper();
                Response response = mapper.readValue(inputData, Response.class);
                System.out.println(response);
                // logger.debug("Response = {}", response.toString());
    
                result = readResult(response.responseBody);
            }
        } catch (IOException e) {
            logger.error("La connexion a échoué : {}", e.getMessage());
        } catch (InterruptedException e) {
            logger.error("L'attente a été interrompue : {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
    


    public abstract S readResult(final String body) throws IOException;

    public void join() throws InterruptedException {
        self.join();
    }

    public final String getThreadName() {
        return threadName;
    }

    public final N getInfo() {
        return info;
    }

    public final S getResult() {
        return result;
    }
}
