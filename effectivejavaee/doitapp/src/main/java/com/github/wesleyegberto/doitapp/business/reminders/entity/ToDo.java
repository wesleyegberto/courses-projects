package com.github.wesleyegberto.doitapp.business.reminders.entity;

import com.github.wesleyegberto.doitapp.business.validations.CrossCheck;
import com.github.wesleyegberto.doitapp.business.validations.ValidEntity;
import com.github.wesleyegberto.doitapp.business.reminders.control.ToDoAudit;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Version;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
@CrossCheck
@EntityListeners({ToDoAudit.class})
public class ToDo implements ValidEntity {

    @Id
    @GeneratedValue
    private long id;
    @NotNull
    @Size(min = 5, max = 100)
    private String caption;
    private String description;
    @Min(0)
    @Max(100)
    private int priority;
    private boolean done;
    @Version
    private int version;

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

    public int getVersion() {
        return version;
    }

    @Override
    public boolean isValid() {
        return (priority <= 10 || priority > 10 && description != null && !description.isEmpty());
    }

    @Override
    public String toString() {
        return "ToDo{" + "id=" + id + ", caption=" + caption + ", description=" + description
               + ", priority=" + priority + ", done=" + done + ", version=" + version + '}';
    }
}
