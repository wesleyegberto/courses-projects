package com.github.wesleyegberto.doitapp.business.logging.boundary;

import com.github.wesleyegberto.doitapp.business.monitoring.entity.CallEvent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 *
 * @author wesley
 */
public class ControlLogger {

    @Inject
    Event<CallEvent> observers;
    
    @AroundInvoke
    public Object logCall(InvocationContext ic) throws Exception {
        long start = System.currentTimeMillis();
        try {
            return ic.proceed();
        } finally {
            long end = System.currentTimeMillis();
            observers.fire(new CallEvent(ic.getMethod().getName(), (end - start)));
        }
    }
    
}
