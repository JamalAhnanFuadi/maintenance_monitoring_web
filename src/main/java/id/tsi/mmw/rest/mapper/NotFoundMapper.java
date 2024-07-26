package id.tsi.mmw.rest.mapper;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import id.tsi.mmw.rest.model.response.ServiceResponse;

@Provider
public class NotFoundMapper implements ExceptionMapper<NotFoundException> {

    public Response toResponse(NotFoundException exception) {
        return Response.status(Status.NOT_FOUND).entity(new ServiceResponse(Status.NOT_FOUND))
                .type(MediaType.APPLICATION_JSON).build();
    }
}