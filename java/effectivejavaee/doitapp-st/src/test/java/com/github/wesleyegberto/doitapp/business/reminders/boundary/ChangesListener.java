package com.github.wesleyegberto.doitapp.business.reminders.boundary;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.json.JsonObject;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

/**
 *
 * @author wesley
 */
public class ChangesListener extends Endpoint {

    private JsonObject message;
    private CountDownLatch latch = new CountDownLatch(1);
    
    @Override
    public void onOpen(Session session, EndpointConfig config) {
        session.addMessageHandler(JsonObject.class, (message) -> {
            this.message = message;
            latch.countDown();
        });
    }

    public JsonObject getMessage() throws InterruptedException {
        latch.await(1, TimeUnit.MINUTES);
        return message;
    }
}
