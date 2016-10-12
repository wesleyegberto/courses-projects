package com.github.wesleyegberto.doitapp.business.reminders.boundary;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

/**
 *
 * @author wesley
 */
public class ChangesListener extends Endpoint {

    private String message;
    private CountDownLatch latch = new CountDownLatch(1);
    
    @Override
    public void onOpen(Session session, EndpointConfig config) {
        session.addMessageHandler(String.class, (message) -> {
            this.message = message;
            latch.countDown();
            System.out.println("message = " + message);
        });
    }

    public String getMessage() throws InterruptedException {
        latch.await(1, TimeUnit.MINUTES);
        return message;
    }
}
