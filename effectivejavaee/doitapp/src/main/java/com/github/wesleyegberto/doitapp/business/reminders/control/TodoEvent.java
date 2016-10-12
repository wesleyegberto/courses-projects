package com.github.wesleyegberto.doitapp.business.reminders.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Qualifier;

/**
 *
 * @author wesley
 */
@Qualifier
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface TodoEvent {
    
    EventType type();
    
    enum EventType {
        CREATION, UPDATE;
    }
}
