package com.github.wesleyegberto.doitapp.business.reminders.boundary;

import java.net.URI;
import java.util.Arrays;
import javax.json.JsonObject;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author wesley
 */
public class ToDoChangeTrackerIT {

    private WebSocketContainer container;
    private ChangesListener listener;

    @Before
    public void init() throws Exception {
        this.container = ContainerProvider.getWebSocketContainer();
        URI uri = new URI("ws://localhost:8080/doitapp/changes/todo");
        this.listener = new ChangesListener();
        ClientEndpointConfig cec = ClientEndpointConfig.Builder.create()
                                    .decoders(Arrays.asList(JsonDecoder.class))
                                    .build();
        this.container.connectToServer(listener, cec, uri);
    }
    
    @Test
    public void receiveNotifications() throws InterruptedException {
        JsonObject message = this.listener.getMessage();
        System.out.println("message = " + message);
        assertNotNull(message);
        assertThat(message.getString("event"), is("creation"));
    }
    
}
