package com.github.wesleyegberto.doitapp.business.reminders.boundary;

import java.net.URI;
import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import static org.junit.Assert.assertNotNull;
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
        this.container.connectToServer(listener, uri);
    }
    
    @Test
    public void receiveNotifications() throws InterruptedException {
        String message = this.listener.getMessage();
        System.out.println("message = " + message);
        assertNotNull(message);
    }
    
}
