package com.github.wesleyegberto.doitapp;

import javax.ejb.EJBException;
import javax.persistence.OptimisticLockException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author wesley
 */
@Provider
public class EjbExceptionMapper implements ExceptionMapper<EJBException> {

    @Override
    public Response toResponse(EJBException exception) {
        Throwable cause = exception.getCause();
        
        Response unknownError = Response.serverError()
                                    .header("cause", cause.toString())
                                    .build();
        if(cause == null)
            return unknownError;
        
        if(cause instanceof OptimisticLockException)
            return Response.status(Response.Status.CONFLICT)
                            .header("conflict", "Conflict cause: " + cause.getMessage())
                            .build();
        
        return unknownError;
    }
    
}
