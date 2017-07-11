package Geek.inc.client;

/**
 * Created by thoma on 10-7-2017.
 *
 *
 */

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class Exception extends Throwable implements ExceptionMapper<Throwable>
{


    @Override
    public Response toResponse(Throwable exception)
    {
        return Response.status(500).entity("Something bad happened. Please try again !!").type("text/plain").build();
    }
}