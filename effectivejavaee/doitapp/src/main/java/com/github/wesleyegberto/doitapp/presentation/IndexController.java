package com.github.wesleyegberto.doitapp.presentation;

import com.github.wesleyegberto.doitapp.business.reminders.control.ToDoManager;
import com.github.wesleyegberto.doitapp.business.reminders.entity.ToDo;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

/**
 *
 * @author wesley
 */
@Model
public class IndexController {

    @Inject
    ToDoManager manager;
    
    ToDo todo;
    
    @Inject
    Validator validator;
    
    @PostConstruct
    public void init() {
        this.todo = new ToDo();
    }
    
    public ToDo getTodo() {
        return todo;
    }
    
    public void showWarningMessage(String message) {
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, message, message);
        FacesContext.getCurrentInstance().addMessage("", facesMessage);
    }
    
    
    public void save() {
        Set<ConstraintViolation<ToDo>> violations = validator.validate(todo);
        
        if(violations.isEmpty()) {
            manager.save(todo);
        } else {
            violations.forEach(v -> showWarningMessage(v.getMessage()));
        }
    }
}
