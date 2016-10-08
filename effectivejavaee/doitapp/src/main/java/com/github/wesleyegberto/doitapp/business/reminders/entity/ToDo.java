package com.github.wesleyegberto.doitapp.business.reminders.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author wesley
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQuery(name = ToDo.FETCH_ALL, query = "SELECT t FROM ToDo t")
public class ToDo {
    
    @Id
    @GeneratedValue
    private long id;
    private String caption;
    private String description;
    private int priority;
    private boolean done;

    // JPA
    private static final String PREFIX = "reminders.entity.ToDo";
    public static final String FETCH_ALL = PREFIX + ".fetchAll";
    
    public ToDo() {
    }

    public ToDo(String caption, String description, int priority) {
        this.caption = caption;
        this.description = description;
        this.priority = priority;
    }

    public ToDo(long id, String caption, String description, int priority) {
        this.id = id;
        this.caption = caption;
        this.description = description;
        this.priority = priority;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
    
    public boolean getStatus() {
        return this.done;
    }
    
}
