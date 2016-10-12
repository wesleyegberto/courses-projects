package com.github.wesleyegberto.doitapp.business.reminders.control;

import com.github.wesleyegberto.doitapp.business.reminders.entity.ToDo;
import java.io.IOException;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author wesley
 */
@Singleton
@ServerEndpoint(value = "/changes/todo", encoders = {JsonEncoder.class})
@ConcurrencyManagement(ConcurrencyManagementType.BEAN) // no locking
public class ToDoChangeTracker {

    private Session session;
    
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }
    
    @OnClose
    public void onClose(Session session) {
        this.session = null;
    }
    
    public void onChange(@Observes(during = TransactionPhase.AFTER_SUCCESS)
                         @TodoEvent(type = TodoEvent.EventType.CREATION) ToDo todo) throws EncodeException {
        System.out.println("ToDo changed event: " + todo);
        // Send a message
        if(session != null && session.isOpen()) {
            try {
                JsonObject jsonObject = Json.createObjectBuilder()
                                        .add("id", todo.getId())
                                        .add("event", "creation").build();
                session.getBasicRemote().sendObject(jsonObject);
            } catch (IOException ex) {
                // just ignore
            }
        }
    }
}
