package com.github.wesleyegberto.doitapp.business.reminders.control;

import com.github.wesleyegberto.doitapp.business.reminders.entity.ToDo;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;

/**
 *
 * @author wesley
 */
public class ToDoChangeTracker {
   public void onChange(@Observes(during = TransactionPhase.AFTER_SUCCESS) ToDo todo) { 
       System.out.println("ToDo changed event: " + todo);
   }
}
