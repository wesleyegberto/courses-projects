package com.github.wesleyegberto.doitapp.business.monitoring.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author wesley
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class CallEvent {

    private String methodName;
    private long duration;

    public CallEvent(String methodName, long duration) {
        this.methodName = methodName;
        this.duration = duration;
    }

    public String getMethodName() {
        return methodName;
    }

    public long getDuration() {
        return duration;
    }

}
