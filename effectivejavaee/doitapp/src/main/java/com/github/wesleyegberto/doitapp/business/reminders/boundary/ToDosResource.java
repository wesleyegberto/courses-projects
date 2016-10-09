package com.github.wesleyegberto.doitapp.business.reminders.boundary;

import com.github.wesleyegberto.doitapp.business.reminders.control.ToDoManager;
import com.github.wesleyegberto.doitapp.business.reminders.entity.ToDo;
import java.net.URI;
import java.util.List;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author wesley
 */
@Path("/todos")
public class ToDosResource {
    
    @Inject
    ToDoManager manager;
    
    @GET
    public List<ToDo> all() {
        return manager.fetchAll();
    }
    
    @GET
    @Path("{id}")
    public ToDo get(@PathParam("id") long id) {
        return manager.findById(id);
    }
    
    @POST
    public Response save(@Valid ToDo todo, @Context UriInfo info) {
        ToDo savedTodo = manager.save(todo);
        // Builds the URL to get the created ToDo
        long id = savedTodo.getId();
        URI uri = info.getAbsolutePathBuilder().path("/" + id).build();
        
        // Return 201 with the Location of the created entity
        // (HTTP specification https://tools.ietf.org/html/rfc2616#section-9.5)
        return Response.created(uri).build();
    }
    
    @PUT
    @Path("{id}")
    public ToDo update(@PathParam("id") long id, @Valid ToDo todo) {
        todo.setId(id);
        return manager.save(todo);
    }
    
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") long id) {
        manager.deleteById(id);
        return Response.ok().build();
    }
        
    @PUT
    @Path("{id}/status")
    public Response updateStatus(@PathParam("id") long id, JsonObject payloadStatus) {
        if(!payloadStatus.containsKey("done")) {
            return Response.status(Response.Status.BAD_REQUEST)
                            .header("reason", "The body should contains the status")
                            .build();
        }
        
        JsonValue statusValue = payloadStatus.getOrDefault("done", JsonValue.NULL);
        
        if(statusValue.getValueType() != JsonValue.ValueType.TRUE
           && statusValue.getValueType() != JsonValue.ValueType.FALSE) {
            return Response.status(Response.Status.BAD_REQUEST)
                            .header("reason", "The status should be TRUE or FALSE")
                            .build();
        }
        
        boolean status = payloadStatus.getBoolean("done");
        ToDo todo = manager.updateStatus(id, status);
        if(todo == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        
        return Response.ok(todo).build();
    }
}
