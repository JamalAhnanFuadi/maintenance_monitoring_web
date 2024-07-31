package id.tsi.mmw.rest.validator;

import id.tsi.mmw.model.User;

public class UserValidator extends BaseValidator {

    public UserValidator() {
        // Empty Constructor
    }

    public boolean validate(User request) {
        return notNull(request)
                && notNull(request.getFirstname())
                && notNull(request.getLastname())
                && notNull(request.getEmail())
                && notNull(request.getMobileNumber())
                && notNull(request.getDob());
    }
}
