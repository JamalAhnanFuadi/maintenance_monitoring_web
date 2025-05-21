package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.ProductController;
import id.tsi.mmw.model.Product;
import id.tsi.mmw.property.Constants;
import id.tsi.mmw.rest.model.request.ProductRequest;
import id.tsi.mmw.rest.validator.ProductValidator;
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
@Path("products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductService extends BaseService {

    @Inject
    private ProductController productController;

    private ProductValidator validator;

    public ProductService() {
        log = getLogger(this.getClass());
        validator = new ProductValidator();
    }

    @PermitAll
    @GET
    public Response getProductList() {
        final String methodName = "getProductList";
        start(methodName);
        log.info(methodName, "Get product list");

        List<Product> products = productController.getProductList();

        completed(methodName);
        return buildSuccessResponse(products);
    }

    @GET
    @Path("{uid}")
    @PermitAll
    public Response getProduct(@PathParam("uid") String uid) {
        final String methodName = "getProduct";
        start(methodName);

        Response response;
        log.info(methodName, "Get product (" + uid + ")");

        boolean isExist = productController.validateProduct(uid);
        log.debug(methodName, "Product validation : " + isExist);

        if (isExist) {
            Product product = productController.getProduct(uid);
            response = buildSuccessResponse(product);
        } else {
            response = buildBadRequestResponse("Product ID not found");
        }
        completed(methodName);
        return response;
    }

    @POST
    @PermitAll
    public Response addProduct(ProductRequest request) {
        final String methodName = "addProduct";
        Response response = null;
        start(methodName);
        log.info(methodName, JsonHelper.toJson(request));

        boolean validPayload = validator.create(request);
        log.debug(methodName, "Request payload validation : " + validPayload);

        if (validPayload) {
            Product product = new Product();
            product.setDisplayName(request.getDisplayName());
            product.setCategoryUid(request.getCategoryUid());
            product.setBrandUid(request.getBrandUid());
            product.setDescription(request.getDescription());

            boolean inserted = productController.addProduct(product);
            if (inserted) {
                response = buildSuccessResponse();
            } else {
                response = buildBadRequestResponse("Product creation failed");
            }
        } else {
            response = buildBadRequestResponse(Constants.MESSAGE_INVALID_REQUEST);
        }
        completed(methodName);
        return response;
    }

    @PUT
    @PermitAll
    public Response updateProduct(ProductRequest request) {
        final String methodName = "updateProduct";
        start(methodName);

        Response response;
        log.info(methodName, JsonHelper.toJson(request));

        boolean validPayload = validator.update(request);
        log.debug(methodName, "Request payload validation : " + validPayload);


        boolean isExist = productController.validateProduct(request.getUid());
        log.debug(methodName, "Product validation : " + isExist);

        if (isExist) {

            Product product = new Product();
            product.setUid(request.getUid());
            product.setDisplayName(request.getDisplayName());
            product.setCategoryUid(request.getCategoryUid());
            product.setBrandUid(request.getBrandUid());
            product.setDescription(request.getDescription());

            boolean updated = productController.updateProduct(product);
            log.debug(methodName, "Product update : " + updated);
            if (updated) {
                response = buildSuccessResponse();
            } else {
                response = buildBadRequestResponse("Failed update Product");
            }
        } else {
            response = buildBadRequestResponse("Product ID not found");
        }
        completed(methodName);
        return response;
    }

    @DELETE
    @Path("{uid}")
    @PermitAll
    public Response deleteProduct(@PathParam("uid") String uid) {
        final String methodName = "deleteProduct";
        start(methodName);

        Response response;
        log.info(methodName, "Delete Product (" + uid + ")");

        boolean isExist = productController.validateProduct(uid);
        log.debug(methodName, "Product brand validation : " + isExist);

        if (isExist) {
            boolean deleted = productController.deleteProduct(uid);
            log.debug(methodName, "Product deletion : " + deleted);
            if (deleted) {
                response = buildSuccessResponse();
            } else {
                response = buildBadRequestResponse("Product deletion failed");
            }
        } else {
            response = buildBadRequestResponse("Product ID not found");
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

        List<Product> products = productController.getProductList();

        String filename = "product.csv";

        List<String> headerColumns = Arrays.asList("uid", "Product Brand Name", "Description","Brand uid","Brand Name","Category uid","Category Name","Create Date","Modify Date");

        List<CSVRecord> recordList = buildProductCSV(headerColumns, products);

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

    protected List<CSVRecord> buildProductCSV(List<String> headerList, List<Product> products) {
        List<CSVRecord> recordList = new ArrayList<>();

        try {
            for (Product product : products) {
                CSVRecord record = new CSVRecord();
                record.put(headerList.get(0), product.getUid());
                record.put(headerList.get(1), product.getDisplayName());
                record.put(headerList.get(2), product.getDescription());
                record.put(headerList.get(3), product.getBrandUid());
                record.put(headerList.get(4), product.getBrandName());
                record.put(headerList.get(5), product.getCategoryUid());
                record.put(headerList.get(6), product.getCategoryName());
                record.put(headerList.get(7), product.getCreateDt());
                record.put(headerList.get(8), product.getModifyDt());

                // Add the new CSV record to the list of records
                recordList.add(record);
            }
        } catch (Exception ex) {
            log.error("buildProductCSV", ex);
        }

        // Return the list of CSV records
        return recordList;
    }
}
