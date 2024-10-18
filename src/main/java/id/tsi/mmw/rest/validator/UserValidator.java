package id.tsi.mmw.rest.validator;

import id.tsi.mmw.rest.model.request.EmailValidateRequest;
import id.tsi.mmw.rest.model.request.UserRequest;
import id.tsi.mmw.rest.model.request.UserStatusRequest;

public class UserValidator extends PaginationValidator {

    public UserValidator() {
        // Empty Constructor
    }

    public boolean create(UserRequest request) {
        return notNull(request)
                && validate(request.getFirstname())
                && validate(request.getLastname())
                && validate(request.getEmail())
                && validate(request.getMobileNumber())
                && validate(request.getDepartment())
                && validate(request.getAccessGroupUid())
                && validate(request.getDob());
    }
    public boolean update(UserRequest request) {
        return notNull(request)
                && validate(request.getUid())
                && validate(request.getFirstname())
                && validate(request.getLastname())
                && validate(request.getEmail())
                && validate(request.getMobileNumber())
                && validate(request.getDepartment())
                && validate(request.getAccessGroupUid())
                && validate(request.getDob());
    }

    public boolean updateStatus(UserStatusRequest request) {
        return notNull(request)
                && validate(request.getUid());
    }
    public boolean validate(EmailValidateRequest request) {
        return notNull(request) && validate(request.getEmail());
    }
}
