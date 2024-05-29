/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

/**
 *
 * @author Acer
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import models.ErrorMessage;

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataNotFoundException.class);
    
    @Override
    public Response toResponse(DataNotFoundException exception) {
        ErrorMessage msg = new ErrorMessage(exception.getMessage(),404,"http://localhost:8080/Health_System_Api/rest/");
        LOGGER.error( msg.toString());
        return Response.status(Response.Status.NOT_FOUND).entity(msg).build();
    }
}

