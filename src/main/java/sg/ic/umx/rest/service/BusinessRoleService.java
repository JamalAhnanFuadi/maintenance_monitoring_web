package sg.ic.umx.rest.service;

import org.apache.commons.codec.Charsets;
import sg.ic.umx.controller.BusinessRoleController;
import sg.ic.umx.model.ApplicationRole;
import sg.ic.umx.model.BusinessRole;
import sg.ic.umx.rest.model.BusinessRoleImportRequest;
import sg.ic.umx.rest.model.BusinessRoleListResponse;
import sg.ic.umx.util.csv.CSVReader;
import sg.ic.umx.util.csv.CSVRecord;
import sg.ic.umx.util.helper.DateHelper;

import javax.annotation.security.PermitAll;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
@PermitAll
@Path("businessRoles")
@Produces(MediaType.APPLICATION_JSON)
public class BusinessRoleService extends BaseService {

    private final BusinessRoleController businessRoleController;
    private static final String HEADERS = "Name,HR Role,Application,Role";

    public BusinessRoleService() {
        log = getLogger(getClass());
        businessRoleController = new BusinessRoleController();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        final String methodName = "list";
        start(methodName);
        BusinessRoleListResponse response = new BusinessRoleListResponse();
        response.setRoleList(businessRoleController.list());

        completed(methodName);
        return buildSuccessResponse(response);
    }

    @POST
    @Path("import")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response doImport(BusinessRoleImportRequest req) {
        final String methodName = "doImport";
        start(methodName);
        Response response = buildInvalidRequestResponse();

        if (req != null && req.getData() != null) {

            String data = req.getData();

            List<CSVRecord> recordList = null;

            recordList = CSVReader.getInstance().read(data);

            if (!recordList.isEmpty()) {
                // Process CSV
                List<BusinessRole> roleList = convertCSVToBusinessRole(recordList);

                if (!roleList.isEmpty()) {
                    // Clear Current List of Roles
                    businessRoleController.clear();

                    // Create Roles
                    businessRoleController.create(roleList);
                }

                response = buildSuccessResponse();
            }
        }

        completed(methodName);
        return response;
    }

    @GET
    @Path("export")
    public Response export() {
        final String methodName = "export";
        start(methodName);

        String fileDateTime = DateHelper.formatFileDateTime(LocalDateTime.now());

        final String fileName = "Business Roles - " + fileDateTime + ".csv";

        // Retrieve Account Info
        List<BusinessRole> businessRoleList = businessRoleController.list();

        try {
            StreamingOutput stream = new StreamingOutput() {
                @Override
                public void write(OutputStream output) throws IOException {
                    try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(output, Charsets.UTF_8))) {

                        // Header
                        pw.println(HEADERS);

                        businessRoleList.forEach(businessRole -> {
                            businessRole.getRoleList().forEach(role -> {
                                pw.println(businessRole.getName() + "," + businessRole.getHrRole() + "," + role.getApplication() + "," + role.getRole());
                            });
                        });
                    } catch (Exception ex) {
                        log.error(methodName, ex);
                        throw ex;
                    }
                }
            };
            completed(methodName);
            return Response.ok(stream, "text/csv").header("content-disposition", "attachment;filename=" + fileName)
                    .build();

        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        return getErrorResponse();
    }

    private List<BusinessRole> convertCSVToBusinessRole(List<CSVRecord> recordList) {
        List<BusinessRole> roleList = new ArrayList<>();

        if (!recordList.isEmpty()) {

            Map<String, BusinessRole> roleMap = new HashMap<>();

            recordList.forEach(record -> {
                String key = record.get("Name");
                String hrRole = record.get("HR Role");
                String application = record.get("Application");
                String role = record.get("Role");

                if (roleMap.containsKey(key)) {
                    roleMap.get(key).addRole(new ApplicationRole(application, role));
                } else {
                    BusinessRole bizRole = new BusinessRole(generateUUID(), key);
                    bizRole.setDescription(record.get("Description"));
                    bizRole.setHrRole(hrRole);
                    bizRole.addRole(new ApplicationRole(application, role));
                    roleMap.put(key, bizRole);
                }
            });

            roleList = new ArrayList<>(roleMap.values());

        }
        return roleList;
    }
}
