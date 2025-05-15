package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.ProductBrandController;
import id.tsi.mmw.model.ProductBrand;
import id.tsi.mmw.property.Constants;
import id.tsi.mmw.rest.model.request.ProductBrandRequest;
import id.tsi.mmw.rest.validator.ProductBrandValidator;
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
@Path("productBrands")
@Produces(MediaType.APPLICATION_JSON)
public class ProductBrandService extends BaseService {

    @Inject
    private ProductBrandController productBrandController;

    private ProductBrandValidator validator;

    public ProductBrandService() {
        log = getLogger(this.getClass());
        validator = new ProductBrandValidator();
    }

    @PermitAll
    @GET
    public Response getProductBrandList() {
        final String methodName = "getProductBrandList";
        start(methodName);
        log.info(methodName, "Get product brand list");

        List<ProductBrand> productBrands = productBrandController.getProductBrandList();

        completed(methodName);
        return buildSuccessResponse(productBrands);
    }

    @GET
    @Path("{uid}")
    @PermitAll
    public Response getProductBrand(@PathParam("uid") String uid) {
        final String methodName = "getProductBrand";
        start(methodName);

        Response response;
        log.info(methodName, "Get product brand (" + uid + ")");

        boolean isExist = productBrandController.validateProductBrand(uid);
        log.debug(methodName, "Product brand validation : " + isExist);

        if (isExist) {
            ProductBrand productBrand = productBrandController.getProductBrand(uid);
            response = buildSuccessResponse(productBrand);
        } else {
            response = buildBadRequestResponse("Product brand ID not found");
        }
        completed(methodName);
        return response;
    }

    @POST
    @PermitAll
    public Response addProductBrand(ProductBrandRequest request) {
        final String methodName = "addProductBrand";
        Response response = null;
        start(methodName);
        log.info(methodName, JsonHelper.toJson(request));

        boolean validPayload = validator.create(request);
        log.debug(methodName, "Request payload validation : " + validPayload);

        if (validPayload) {
            ProductBrand productBrand = new ProductBrand();
            productBrand.setDisplayName(request.getDisplayName());
            productBrand.setDescription(request.getDescription());

            boolean inserted = productBrandController.addProductBrand(productBrand);
            if (inserted) {
                response = buildSuccessResponse();
            } else {
                response = buildBadRequestResponse("Product brand creation failed");
            }
        } else {
            response = buildBadRequestResponse(Constants.MESSAGE_INVALID_REQUEST);
        }
        completed(methodName);
        return response;
    }

    @PUT
    @PermitAll
    public Response updateProductBrand(ProductBrandRequest request) {
        final String methodName = "updateProductBrand";
        start(methodName);

        Response response;
        log.info(methodName, JsonHelper.toJson(request));

        boolean validPayload = validator.update(request);
        log.debug(methodName, "Request payload validation : " + validPayload);


        boolean isExist = productBrandController.validateProductBrand(request.getUid());
        log.debug(methodName, "Product brand validation : " + isExist);

        if (isExist) {

            ProductBrand productBrand = new ProductBrand();
            productBrand.setUid(request.getUid());
            productBrand.setDisplayName(request.getDisplayName());
            productBrand.setDescription(request.getDescription());

            boolean updated = productBrandController.updateProductBrand(productBrand);
            log.debug(methodName, "Product brand update : " + updated);
            if (updated) {
                response = buildSuccessResponse();
            } else {
                response = buildBadRequestResponse("Failed update Product brand");
            }
        } else {
            response = buildBadRequestResponse("Product brand ID not found");
        }
        completed(methodName);
        return response;
    }

    @DELETE
    @Path("{uid}")
    @PermitAll
    public Response deleteProductBrand(@PathParam("uid") String uid) {
        final String methodName = "deleteProductBrand";
        start(methodName);

        Response response;
        log.info(methodName, "Delete Product brand (" + uid + ")");

        boolean isExist = productBrandController.validateProductBrand(uid);
        log.debug(methodName, "Product brand validation : " + isExist);

        if (isExist) {
            boolean deleted = productBrandController.deleteProductBrand(uid);
            log.debug(methodName, "Product brand deletion : " + deleted);
            if (deleted) {
                response = buildSuccessResponse();
            } else {
                response = buildBadRequestResponse("Product brand deletion failed");
            }
        } else {
            response = buildBadRequestResponse("Product brand ID not found");
        }
        completed(methodName);
        return response;
    }

    @GET
    @Path("/export")
    public Response exportProductBrand() {
        final String methodName = "exportProductBrand";
        start(methodName);
        log.info(methodName, "Export product brand");

        List<ProductBrand> productBrands = productBrandController.getProductBrandList();

        String filename = "product-brands.csv";

        List<String> headerColumns = Arrays.asList("uid", "Product Brand Name", "Description");

        List<CSVRecord> recordList = buildProductBrandCSV(headerColumns, productBrands);

        StreamingOutput stream = new StreamingOutput() {
            @Override
            public void write(OutputStream output) throws IOException {
                try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(output, Charsets.UTF_8))) {
                    // Write the report to the output stream
                    CSVWriter.getInstance().write(pw, headerColumns, recordList);
                }
            }
        };

        completed(methodName);
        return Response.ok(stream, "text/csv").header("content-disposition", "attachment;filename=" + filename).build();
    }

    protected List<CSVRecord> buildProductBrandCSV(List<String> headerList, List<ProductBrand> productBrands) {
        List<CSVRecord> recordList = new ArrayList<>();

        try {
            for (ProductBrand productBrand : productBrands) {
                CSVRecord record = new CSVRecord();
                record.put(headerList.get(0), productBrand.getUid());
                record.put(headerList.get(1), productBrand.getDisplayName());
                record.put(headerList.get(2), productBrand.getDescription());

                // Add the new CSV record to the list of records
                recordList.add(record);
            }
        } catch (Exception ex) {
            log.error("buildProductBrandCSV", ex);
        }

        // Return the list of CSV records
        return recordList;
    }
}
