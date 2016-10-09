package com.github.wesleyegberto.doitapp.business.reminders.entity;

import com.github.wesleyegberto.doitapp.business.CrossCheck;
import com.github.wesleyegberto.doitapp.business.ValidEntity;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.mail.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.Entity;
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
public class ToDo implements ValidEntity {
    
    @Id
    @GeneratedValue
    private long id;
    @NotNull @Size(min = 5, max = 100)
    private String caption;
    private String description;
    @Min(0) @Max(100)
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

    public void setVersion(int version) {
        this.version = version;
    }

    private Session getS() throws NamingException {
        Context c = new InitialContext();
        return (Session) c.lookup("java:comp/env/s");
    }

    private Message createJMSMessageFord(javax.jms.Session session, Object messageData) throws JMSException {
        // TODO create and populate message to send
        TextMessage tm = session.createTextMessage();
        tm.setText(messageData.toString());
        return tm;
    }

    @Override
    public boolean isValid() {
        return (priority > 10 && description != null || priority <= 10);
    }

}
