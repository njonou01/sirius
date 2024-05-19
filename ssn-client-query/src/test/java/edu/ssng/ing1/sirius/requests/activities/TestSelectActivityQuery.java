package edu.ssng.ing1.sirius.requests.activities;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import edu.ssng.ing1.sirius.business.dto.Activites;
import edu.ssng.ing1.sirius.client.commons.ConfigLoader;
import edu.ssng.ing1.sirius.client.commons.NetworkConfig;
import edu.ssng.ing1.sirius.commons.Request;
import static org.powermock.api.mockito.PowerMockito.whenNew;

public class TestSelectActivityQuery {

    private NetworkConfig networkConfig;
    private Request request;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        networkConfig = mock(NetworkConfig.class);
        request = mock(Request.class);
        objectMapper = mock(ObjectMapper.class);
    }

    @Test
    public void testSelectActivity() throws Exception {
        // Setup
        String requestId = UUID.randomUUID().toString();
        byte[] requestBytes = new byte[10]; // Mocked request bytes
        Activites expectedActivites = new Activites(); // Mocked expected activites

        // Mocking behavior
        when(ConfigLoader.loadConfig(eq(NetworkConfig.class), anyString())).thenReturn(networkConfig);
        when(networkConfig.toString()).thenReturn("Mocked network config");
        when(request.getRequestId()).thenReturn(requestId); // Appel de méthode ajouté
        when(request.getRequestOrder()).thenReturn("SELECT_ALL_ACTIVITY");
        when(objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE)).thenReturn(objectMapper);
        when(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request)).thenReturn(requestBytes);

        SelectActivitesClientRequest mockedClientRequest = mock(SelectActivitesClientRequest.class);
        whenNew(SelectActivitesClientRequest.class).withArguments(networkConfig, anyInt(), request, null, requestBytes)
                .thenReturn(mockedClientRequest);
        when(mockedClientRequest.getResult()).thenReturn(expectedActivites);

        
        Activites result = SelectActivityQuery.SelectActivite();

       
        assertEquals(expectedActivites, result);
    }
}
