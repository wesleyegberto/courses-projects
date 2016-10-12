/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.wesleyegberto.doitapp.business.monitoring.boundary;

import com.github.wesleyegberto.doitapp.business.logging.boundary.LogSink;
import com.github.wesleyegberto.doitapp.business.monitoring.entity.CallEvent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 *
 * @author wesley
 */
public class CallMonitorSink {
    
    @Inject
    LogSink logger;
    
    public void onCallEvent(@Observes CallEvent event) {
       logger.log(String.format("Method called: %s ran in %d ms",
                                event.getMethodName(), event.getDuration()));
    }
}
