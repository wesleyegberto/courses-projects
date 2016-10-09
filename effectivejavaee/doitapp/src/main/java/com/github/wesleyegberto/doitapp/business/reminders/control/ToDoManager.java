package com.github.wesleyegberto.doitapp.business.reminders.control;

import com.github.wesleyegberto.doitapp.business.logging.boundary.ControlLogger;
import com.github.wesleyegberto.doitapp.business.reminders.entity.ToDo;
import java.util.List;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author wesley
 */
@Stateless
@Interceptors(ControlLogger.class)
public class ToDoManager {
    
    @PersistenceContext
    EntityManager em;
    
    public ToDo findById(long id) {
        return this.em.find(ToDo.class, id);
    }

    public ToDo save(ToDo todo) {
        return this.em.merge(todo);
    }

    public void deleteById(long id) {
        // Get a reference (proxy) even if doesn't exist
        // so we don't need to verify if is null, just remove
        
        try {
            ToDo reference = this.em.getReference(ToDo.class, id);
            this.em.remove(reference);
        } catch (EntityNotFoundException ex) {
            // we are just delete
        }
    }

    public List<ToDo> fetchAll() {
        return this.em.createNamedQuery(ToDo.FETCH_ALL, ToDo.class)
                    .getResultList();
    }

    public ToDo updateStatus(long id, boolean done) {
        ToDo todo = this.em.find(ToDo.class, id);
        if(todo == null)
            return null;
        todo.setDone(done);
        return this.em.merge(todo);
    }
}
