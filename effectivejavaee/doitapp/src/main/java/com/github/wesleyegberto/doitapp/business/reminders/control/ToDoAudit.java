package com.github.wesleyegberto.doitapp.business.reminders.control;

import com.github.wesleyegberto.doitapp.business.reminders.entity.ToDo;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.PostPersist;

/**
 *
 * @author wesley
 */
public class ToDoAudit {
    @Inject
    Event<ToDo> listeners;
    
    @PostPersist
    public void auditChange(ToDo todo) {
        System.out.println("ToDo changed audited: " + todo);
        listeners.fire(todo);
    }
}
