package com.airhacks.mon.business.reporting.boundary;

import javax.json.JsonObject;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.UUID;

import org.junit.Test;

/**
 *
 * @author airhacks.com
 */
public class SnapshotsResourceIT extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(SnapshotsResource.class);
    }

    @Test
    public void snapshots() {
    	UUID id = UUID.randomUUID();
        Response response = target("snapshots/" + id).request().get();
        assertThat(response.getStatus(), is(204));
        JsonObject result = response.readEntity(JsonObject.class);
        System.out.println("result = " + result);
    }

}
