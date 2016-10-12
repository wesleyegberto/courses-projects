package com.github.wesleyegberto.doitapp.business.monitoring.entity;

/**
 *
 * @author wesley
 */
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
