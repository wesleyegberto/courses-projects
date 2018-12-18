package com.github.wesleyegberto.doitapp.business.logging.boundary;

import java.util.logging.Logger;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 *
 * @author wesley
 */
public class LoggerFactory {

    @Produces
    public LogSink produce(InjectionPoint ip) {
        Class<?> injectionTarget = ip.getMember().getDeclaringClass();
        return Logger.getLogger(injectionTarget.getName())::info;
    }
    
}
