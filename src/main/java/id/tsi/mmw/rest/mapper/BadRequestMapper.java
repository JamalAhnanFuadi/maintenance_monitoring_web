package id.tsi.mmw.rest.mapper;

import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import id.tsi.mmw.rest.model.response.ServiceResponse;

@Provider
public class BadRequestMapper implements ExceptionMapper<ValidationException> {

    public Response toResponse(ValidationException exception) {
        return Response.status(Status.BAD_REQUEST).entity(new ServiceResponse(Status.BAD_REQUEST)).build();
    }
}
