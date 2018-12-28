package com.github.wesleyegberto.doitapp.business.reminders.control;

import com.github.wesleyegberto.doitapp.business.reminders.entity.ToDo;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

/**
 *
 * @author wesley
 */
public class ToDoAudit {
    
    @Inject @TodoEvent(type = TodoEvent.EventType.CREATION)
    Event<ToDo> creationListeners;
    
    @Inject @TodoEvent(type = TodoEvent.EventType.UPDATE)
    Event<ToDo> updateListeners;
    
    @PostPersist
    public void auditCreation(ToDo todo) {
        System.out.println("ToDo creation audited: " + todo);
        creationListeners.fire(todo);
    }
    
    @PostUpdate
    public void auditChange(ToDo todo) {
        System.out.println("ToDo changed audited: " + todo);
        updateListeners.fire(todo);
    }
}
