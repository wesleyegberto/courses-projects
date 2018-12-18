package com.github.wesleyegberto.doitapp.business.monitoring.boundary;

import com.github.wesleyegberto.doitapp.business.monitoring.entity.CallEvent;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author wesley
 */
@Path("calls-monitor")
public class CallsMonitorResource {
 
    @Inject
    private CallMonitorSink monitor;
    
    @GET
    public List<CallEvent> getRecentsCalls() {
        return monitor.getRecentsEvents();
    }
    
    
}
