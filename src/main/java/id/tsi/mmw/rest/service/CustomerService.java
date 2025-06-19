package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.CustomerController;
import id.tsi.mmw.model.Customer;
import id.tsi.mmw.property.Constants;
import id.tsi.mmw.rest.model.request.CustomerRequest;
import id.tsi.mmw.rest.validator.CustomerValidator;
import id.tsi.mmw.util.json.JsonHelper;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Singleton
@Path("customers")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerService extends BaseService {

    @Inject
    private CustomerController customerController;

    private CustomerValidator validator;

    public CustomerService() {
        log = getLogger(this.getClass());
        validator = new CustomerValidator();
    }

    @PermitAll
    @GET
    public Response getCustomerList() {
        final String methodName = "getCustomerList";
        start(methodName);
        log.info(methodName, "Get Customer List");

        List<Customer> Customers = customerController.getCustomerList();

        completed(methodName);
        return buildSuccessResponse(Customers);
    }

    @GET
    @Path("{uid}")
    @PermitAll
    public Response getCustomer(@PathParam("uid") String uid) {
        final String methodName = "getCustomer";
        start(methodName);

        Response response;
        log.info(methodName, "Get Customer (" + uid + ")");

        boolean isExist = customerController.validateCustomer(uid);
        log.debug(methodName, "Customer validation : " + isExist);

        if (isExist) {
            Customer  customer = customerController.getCustomer(uid);
            response = buildSuccessResponse(customer);
        } else {
            response = buildBadRequestResponse("Customer ID not found");
        }
        completed(methodName);
        return response;
    }

    @POST
    @PermitAll
    public Response addCustomer(CustomerRequest request) {
        final String methodName = "addCustomer";
        Response response = null;
        start(methodName);
        log.info(methodName, JsonHelper.toJson(request));

        boolean validPayload = validator.create(request);
        log.debug(methodName, "Request payload validation : " + validPayload);

        if (validPayload) {
            Customer customer= new Customer();
            customer.setDisplayName(request.getDisplayName());
            customer.setAddress(request.getAddress());
            customer.setEmail(request.getEmail());
            customer.setPhone(request.getPhone());
            customer.setWebsite(request.getWebsite());

            boolean inserted = customerController.addCustomer(customer);
            if (inserted) {
                response = buildSuccessResponse();
            } else {
                response = buildBadRequestResponse("Customer creation failed");
            }
        } else {
            response = buildBadRequestResponse(Constants.MESSAGE_INVALID_REQUEST);
        }
        completed(methodName);
        return response;
    }

    @PUT
    @PermitAll
    public Response updateCustomer(CustomerRequest request) {
        final String methodName = "updateCustomer";
        start(methodName);

        Response response;
        log.info(methodName, JsonHelper.toJson(request));

        boolean validPayload = validator.update(request);
        log.debug(methodName, "Request payload validation : " + validPayload);


        boolean isExist = customerController.validateCustomer(request.getUid());
        log.debug(methodName, "Customer validation : " + isExist);

        if (isExist) {

            Customer customer = new Customer();
            customer.setUid(request.getUid());
            customer.setDisplayName(request.getDisplayName());
            customer.setAddress(request.getAddress());
            customer.setEmail(request.getEmail());
            customer.setPhone(request.getPhone());
            customer.setWebsite(request.getWebsite());
            customer.setStatus(request.isStatus());

            boolean deleted = customerController.updateCustomer(customer);
            log.debug(methodName, "Customer update : " + deleted);
            if (deleted) {
                response = buildSuccessResponse();
            } else {
                response = buildBadRequestResponse("Failed update Customer type");
            }
        } else {
            response = buildBadRequestResponse("Customer ID not found");
        }
        completed(methodName);
        return response;
    }

    @DELETE
    @Path("{uid}")
    @PermitAll
    public Response deleteCustomer(@PathParam("uid") String uid) {
        final String methodName = "deleteCustomer";
        start(methodName);

        Response response;
        log.info(methodName, "Delete customer (" + uid + ")");

        boolean isExist = customerController.validateCustomer(uid);
        log.debug(methodName, "Customer validation : " + isExist);

        if (isExist) {
            boolean deleted = customerController.deleteCustomer(uid);
            log.debug(methodName, "Customer deletion : " + deleted);
            if (deleted) {
                response = buildSuccessResponse();
            } else {
                response = buildBadRequestResponse("Customer deletion failed");
            }
        } else {
            response = buildBadRequestResponse("Customer ID not found");
        }
        completed(methodName);
        return response;
    }

}
