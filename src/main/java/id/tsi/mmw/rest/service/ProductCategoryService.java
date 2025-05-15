package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.ProductCategoryController;
import id.tsi.mmw.model.ProductCategory;
import id.tsi.mmw.property.Constants;
import id.tsi.mmw.rest.model.request.ProductCategoryRequest;
import id.tsi.mmw.rest.validator.ProductCategoryValidator;
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
@Path("productCategories")
@Produces(MediaType.APPLICATION_JSON)
public class ProductCategoryService extends BaseService {

    @Inject
    private ProductCategoryController productCategoryController;

    private ProductCategoryValidator validator;

    public ProductCategoryService() {
        log = getLogger(this.getClass());
        validator = new ProductCategoryValidator();
    }

    @PermitAll
    @GET
    public Response getProductCategoryList() {
        final String methodName = "getProductCategoryList";
        start(methodName);
        log.info(methodName, "Get Product category list");

        List<ProductCategory> productCategories = productCategoryController.getProductCategoryList();

        completed(methodName);
        return buildSuccessResponse(productCategories);
    }

    @GET
    @Path("{uid}")
    @PermitAll
    public Response getProductBrand(@PathParam("uid") String uid) {
        final String methodName = "getProductBrand";
        start(methodName);

        Response response;
        log.info(methodName, "Get Product category (" + uid + ")");

        boolean isExist = productCategoryController.validateProductCategory(uid);
        log.debug(methodName, "Product category validation : " + isExist);

        if (isExist) {
            ProductCategory productCategory = productCategoryController.getProductCategory(uid);
            response = buildSuccessResponse(productCategory);
        } else {
            response = buildBadRequestResponse("Product category ID not found");
        }
        completed(methodName);
        return response;
    }

    @POST
    @PermitAll
    public Response addProductCategory(ProductCategoryRequest request) {
        final String methodName = "addProductCategory";
        Response response = null;
        start(methodName);
        log.info(methodName, JsonHelper.toJson(request));

        boolean validPayload = validator.create(request);
        log.debug(methodName, "Request payload validation : " + validPayload);

        if (validPayload) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setDisplayName(request.getDisplayName());
            productCategory.setDescription(request.getDescription());

            boolean inserted = productCategoryController.addProductCategory(productCategory);
            if (inserted) {
                response = buildSuccessResponse();
            } else {
                response = buildBadRequestResponse("Product category creation failed");
            }
        } else {
            response = buildBadRequestResponse(Constants.MESSAGE_INVALID_REQUEST);
        }
        completed(methodName);
        return response;
    }

    @PUT
    @PermitAll
    public Response updateProductCategory(ProductCategoryRequest request) {
        final String methodName = "updateProductCategory";
        start(methodName);

        Response response;
        log.info(methodName, JsonHelper.toJson(request));

        boolean validPayload = validator.update(request);
        log.debug(methodName, "Request payload validation : " + validPayload);


        boolean isExist = productCategoryController.validateProductCategory(request.getUid());
        log.debug(methodName, "Product category validation : " + isExist);

        if (isExist) {

            ProductCategory productCategory = new ProductCategory();
            productCategory.setUid(request.getUid());
            productCategory.setDisplayName(request.getDisplayName());
            productCategory.setDescription(request.getDescription());

            boolean updated = productCategoryController.updateProductCategory(productCategory);
            log.debug(methodName, "Product category update : " + updated);
            if (updated) {
                response = buildSuccessResponse();
            } else {
                response = buildBadRequestResponse("Failed update Product category");
            }
        } else {
            response = buildBadRequestResponse("Product category ID not found");
        }
        completed(methodName);
        return response;
    }

    @DELETE
    @Path("{uid}")
    @PermitAll
    public Response deleteProductCategory(@PathParam("uid") String uid) {
        final String methodName = "deleteProductCategory";
        start(methodName);

        Response response;
        log.info(methodName, "Delete Product category (" + uid + ")");

        boolean isExist = productCategoryController.validateProductCategory(uid);
        log.debug(methodName, "Product category validation : " + isExist);

        if (isExist) {
            boolean deleted = productCategoryController.deleteProductCategory(uid);
            log.debug(methodName, "Product category deletion : " + deleted);
            if (deleted) {
                response = buildSuccessResponse();
            } else {
                response = buildBadRequestResponse("Product category deletion failed");
            }
        } else {
            response = buildBadRequestResponse("Product category ID not found");
        }
        completed(methodName);
        return response;
    }

    @GET
    @Path("/export")
    public Response exportProductCategory() {
        final String methodName = "exportProductCategory";
        start(methodName);
        log.info(methodName, "Export Product category");

        List<ProductCategory> productCategories = productCategoryController.getProductCategoryList();

        String filename = "product-categories.csv";

        List<String> headerColumns = Arrays.asList("uid", "Product category Name", "Description");

        List<CSVRecord> recordList = buildProductCategoryCSV(headerColumns, productCategories);

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

    protected List<CSVRecord> buildProductCategoryCSV(List<String> headerList, List<ProductCategory> productCategories) {
        List<CSVRecord> recordList = new ArrayList<>();

        try {
            for (ProductCategory productCategory : productCategories) {
                CSVRecord record = new CSVRecord();
                record.put(headerList.get(0), productCategory.getUid());
                record.put(headerList.get(1), productCategory.getDisplayName());
                record.put(headerList.get(2), productCategory.getDescription());

                // Add the new CSV record to the list of records
                recordList.add(record);
            }
        } catch (Exception ex) {
            log.error("buildProductCategoryCSV", ex);
        }

        // Return the list of CSV records
        return recordList;
    }
}
