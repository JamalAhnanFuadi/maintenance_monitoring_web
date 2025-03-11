package id.tsi.mmw.rest.validator;

import id.tsi.mmw.rest.model.request.DepartmentRequest;
import id.tsi.mmw.rest.model.request.EmailValidateRequest;
import id.tsi.mmw.rest.model.request.UserRequest;
import id.tsi.mmw.rest.model.request.UserStatusRequest;

public class DepartmentValidator extends PaginationValidator {

    public DepartmentValidator() {
        // Empty Constructor
    }

    public boolean create(DepartmentRequest request) {
        return notNull(request)
                && validate(request.getDisplayName());
    }
    public boolean update(DepartmentRequest request) {
        return notNull(request)
                && validate(request.getUid())
                && validate(request.getDisplayName());
    }
}
