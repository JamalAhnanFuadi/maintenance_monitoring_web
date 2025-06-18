package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.CustomerTypeController;
import id.tsi.mmw.controller.DepartmentController;
import id.tsi.mmw.model.CustomerType;
import id.tsi.mmw.model.Department;
import id.tsi.mmw.property.Constants;
import id.tsi.mmw.rest.model.CustomerTypeRequest;
import id.tsi.mmw.rest.model.request.DepartmentRequest;
import id.tsi.mmw.rest.validator.CustomerTypeValidator;
import id.tsi.mmw.rest.validator.DepartmentValidator;
import id.tsi.mmw.util.csv.CSVRecord;
import id.tsi.mmw.util.csv.CSVWriter;
import id.tsi.mmw.util.json.JsonHelper;
import org.apache.commons.codec.Charsets;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Singleton
@Path("customertypes")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerTypeService extends BaseService {

    @Inject
    private CustomerTypeController customerTypeController;

    private CustomerTypeValidator validator;

    public CustomerTypeService() {
        log = getLogger(this.getClass());
        validator = new CustomerTypeValidator();
    }

    @PermitAll
    @GET
    public Response getCustomerTypeList() {
        final String methodName = "getCustomerTypeList";
        start(methodName);
        log.info(methodName, "Get Customer type List");

        List<CustomerType> customerTypes = customerTypeController.getCustomerTypeList();

        completed(methodName);
        return buildSuccessResponse(customerTypes);
    }

    @GET
    @Path("{uid}")
    @PermitAll
    public Response getDepartment(@PathParam("uid") String uid) {
        final String methodName = "getDepartment";
        start(methodName);

        Response response;
        log.info(methodName, "Get Customer type (" + uid + ")");

        boolean isExist = customerTypeController.validateCustomerType(uid);
        log.debug(methodName, "Customer type validation : " + isExist);

        if (isExist) {
            CustomerType customerType = customerTypeController.getCustomerType(uid);
            response = buildSuccessResponse(customerType);
        } else {
            response = buildBadRequestResponse("Customer type ID not found");
        }
        completed(methodName);
        return response;
    }

    @POST
    @PermitAll
    public Response addCustomerType(CustomerTypeRequest request) {
        final String methodName = "addCustomerType";
        Response response = null;
        start(methodName);
        log.info(methodName, JsonHelper.toJson(request));

        boolean validPayload = validator.create(request);
        log.debug(methodName, "Request payload validation : " + validPayload);

        if (validPayload) {
            CustomerType customerType = new CustomerType();
            customerType.setDisplayName(request.getDisplayName());
            customerType.setDescription(request.getDescription());

            boolean inserted = customerTypeController.addCustomerType(customerType);
            if (inserted) {
                response = buildSuccessResponse();
            } else {
                response = buildBadRequestResponse("Customer type creation failed");
            }
        } else {
            response = buildBadRequestResponse(Constants.MESSAGE_INVALID_REQUEST);
        }
        completed(methodName);
        return response;
    }

    @PUT
    @PermitAll
    public Response updateCustomerType(CustomerTypeRequest request) {
        final String methodName = "updateCustomerType";
        start(methodName);

        Response response;
        log.info(methodName, JsonHelper.toJson(request));

        boolean validPayload = validator.update(request);
        log.debug(methodName, "Request payload validation : " + validPayload);


        boolean isExist = customerTypeController.validateCustomerType(request.getUid());
        log.debug(methodName, "Customer type validation : " + isExist);

        if (isExist) {

            CustomerType customerType = new CustomerType();
            customerType.setUid(request.getUid());
            customerType.setDisplayName(request.getDisplayName());
            customerType.setDescription(request.getDescription());

            boolean deleted = customerTypeController.updateCustomerType(customerType);
            log.debug(methodName, "Customer type update : " + deleted);
            if (deleted) {
                response = buildSuccessResponse();
            } else {
                response = buildBadRequestResponse("Failed update Customer type");
            }
        } else {
            response = buildBadRequestResponse("Customer type ID not found");
        }
        completed(methodName);
        return response;
    }

    @DELETE
    @Path("{uid}")
    @PermitAll
    public Response deleteCustomerType(@PathParam("uid") String uid) {
        final String methodName = "deleteCustomerType";
        start(methodName);

        Response response;
        log.info(methodName, "Delete customer type (" + uid + ")");

        boolean isExist = customerTypeController.validateCustomerType(uid);
        log.debug(methodName, "Customer type validation : " + isExist);

        if (isExist) {
            boolean deleted = customerTypeController.deleteCustomerType(uid);
            log.debug(methodName, "Customer type deletion : " + deleted);
            if (deleted) {
                response = buildSuccessResponse();
            } else {
                response = buildBadRequestResponse("Customer type deletion failed");
            }
        } else {
            response = buildBadRequestResponse("Customer type ID not found");
        }
        completed(methodName);
        return response;
    }

}
