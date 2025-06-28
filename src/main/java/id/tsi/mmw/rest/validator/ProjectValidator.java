package id.tsi.mmw.rest.validator;

import id.tsi.mmw.rest.model.request.ProjectRequest;

public class ProjectValidator extends PaginationValidator {

    public ProjectValidator() {
        // Empty Constructor
    }

    public boolean create(ProjectRequest request) {
        return notNull(request)
                && validate(request.getProjectName())
                && validate(request.getCustomerUid())
                && validate(request.getStaffPic());
    }
    public boolean update(ProjectRequest request) {
        return notNull(request)
                && validate(request.getUid())
                && validate(request.getProjectName())
                && validate(request.getCustomerUid())
                && validate(request.getStaffPic());
    }
}
