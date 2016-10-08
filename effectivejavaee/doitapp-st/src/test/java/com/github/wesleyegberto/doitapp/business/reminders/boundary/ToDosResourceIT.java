package com.github.wesleyegberto.doitapp.business.reminders.boundary;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author wesley
 */
public class ToDosResourceIT {

    private Client client;
    // target under test
    private WebTarget tut;
    
    @Before
    public void init() {
        this.client = ClientBuilder.newClient();
        this.tut = client.target("http://localhost:8080/doitapp/api/todos");
    }
    
    @Test
    public void should_crud_todo() {
        // Test the Create
        final String CAPTION = "Change to use entity class";
        JsonObject todoToCreate = Json.createObjectBuilder()
                                    .add("caption", CAPTION)
                                    .add("description", "...")
                                    .add("priority", 10)
                                    .build();
        
        Response response = this.tut.request().post(Entity.json(todoToCreate));
        assertThat(response.getStatus(), is(201));
        final String location = response.getHeaderString("Location");
        assertNotNull(response.getHeaderString("Location"));

        // Test the Retrieve of created todo
        JsonObject createdTodo = this.client.target(location)
                                        .request(MediaType.APPLICATION_JSON)
                                        .get(JsonObject.class);
        assertNotNull(createdTodo);
        final int idCreatedTodo = createdTodo.getInt("id");
        assertTrue(idCreatedTodo > 0);
        assertThat(createdTodo.getString("caption"), is(CAPTION));
        
        
        // Test the Update
        final String NEW_CAPTION = "Changed to use entity class";
        JsonObject todoToUpdate = Json.createObjectBuilder()
                                    .add("caption", NEW_CAPTION)
                                    .add("description", "...")
                                    .add("priority", 10)
                                    .build();
        response = this.client.target(location)
                    .request(MediaType.APPLICATION_JSON)
                    .put(Entity.json(todoToUpdate));
        assertThat(response.getStatus(), is(200));
        
        // Assert the update
        JsonObject updatedTodo = this.client.target(location)
                                        .request(MediaType.APPLICATION_JSON)
                                        .get(JsonObject.class);
        assertNotNull(updatedTodo);
        assertThat(updatedTodo.getString("caption"), is(NEW_CAPTION));
        
        
        // Test the Update Status
        JsonObject updatedStatus = Json.createObjectBuilder().add("done", true).build();
        response = this.client.target(location)
                        .path("status")
                        .request(MediaType.APPLICATION_JSON)
                        .put(Entity.json(updatedStatus));
        assertThat(response.getStatus(), is(200));
        
        // Assert the updated status
        JsonObject updatedStatusTodo = this.client.target(location)
                                            .request(MediaType.APPLICATION_JSON)
                                            .get(JsonObject.class);
        assertNotNull(updatedStatusTodo);
        assertThat(updatedStatusTodo.getBoolean("done"), is(true));
        
        
        // Test the Update Status of a non-existing todo
        updatedStatus = Json.createObjectBuilder().add("done", true).build();
        response = this.tut.path("-42222").path("status")
                        .request(MediaType.APPLICATION_JSON)
                        .put(Entity.json(updatedStatus));
        assertThat(response.getStatus(), is(404));
        
        
        // Test the Update Status with an invalid request
        updatedStatus = Json.createObjectBuilder().add("no_status", true).build();
        response = this.client.target(location)
                        .path("status")
                        .request(MediaType.APPLICATION_JSON)
                        .put(Entity.json(updatedStatus));
        assertThat(response.getStatus(), is(400));
        assertNotNull(response.getHeaderString("reason"));
        updatedStatus = Json.createObjectBuilder().add("done", "weird value").build();
        response = this.client.target(location)
                        .path("status")
                        .request(MediaType.APPLICATION_JSON)
                        .put(Entity.json(updatedStatus));
        assertThat(response.getStatus(), is(400));
        assertNotNull(response.getHeaderString("reason"));
        
        
        // Test the Retrieve all
        response = this.tut.request(MediaType.APPLICATION_JSON).get();
        JsonArray allTodos = response.readEntity(JsonArray.class);
        assertNotNull(allTodos);
        assertFalse(allTodos.isEmpty());
        
        
        // Test Delete
        Response deleteResponse = this.tut.path(String.valueOf(idCreatedTodo))
                                .request()
                                .delete();
        assertThat(deleteResponse.getStatus(), is(200));
    }
    
    @Test
    public void should_got_todo_not_found() {
        Response response = this.tut.path("/-42")
                                .request(MediaType.APPLICATION_JSON)
                                .get();
        assertThat(response.getStatus(), is(204));
    }
    
}
