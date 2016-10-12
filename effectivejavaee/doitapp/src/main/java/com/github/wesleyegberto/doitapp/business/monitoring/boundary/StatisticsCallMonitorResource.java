package com.github.wesleyegberto.doitapp.business.monitoring.boundary;

import java.util.LongSummaryStatistics;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author wesley
 */
@Path("statistics-calls")
public class StatisticsCallMonitorResource {

    @Inject
    CallMonitorSink monitor;
    
    @GET
    public JsonObject getStatistics() {
        LongSummaryStatistics statistics = monitor.getStatistics();
        return Json.createObjectBuilder()
                .add("average", statistics.getAverage())
                .add("sum", statistics.getSum())
                .add("min", statistics.getMin())
                .add("max", statistics.getMax())
                .build();
    }
    
}
