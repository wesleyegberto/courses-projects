/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.wesleyegberto.doitapp.business.monitoring.boundary;

import com.github.wesleyegberto.doitapp.business.logging.boundary.LogSink;
import com.github.wesleyegberto.doitapp.business.monitoring.entity.CallEvent;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 *
 * @author wesley
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN) // to not block nothing
public class CallMonitorSink {
    
    @Inject
    LogSink logger;
    
    // Will allow updates from many thread without throw a ConcurrentModificationException
    CopyOnWriteArrayList<CallEvent> recentsEvents;

    @PostConstruct
    public void init() {
        this.recentsEvents = new CopyOnWriteArrayList<>();
    }
    
    public CopyOnWriteArrayList<CallEvent> getRecentsEvents() {
        return recentsEvents;
    }
    
    public void onCallEvent(@Observes CallEvent event) {
       logger.log(String.format("Method called: %s ran in %d ms",
                                event.getMethodName(), event.getDuration()));
       recentsEvents.add(event);
    }
}
