package com.github.wesleyegberto.doitapp.business.logging.boundary;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 *
 * @author wesley
 */
public class ControlLogger {

    @Inject
    LogSink logger;
    
    @AroundInvoke
    public Object logCall(InvocationContext ic) throws Exception {
        logger.log("-- Called: " + ic.getMethod().getName());
        return ic.proceed();
    }
    
}
