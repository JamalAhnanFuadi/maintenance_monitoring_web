package id.tsi.mmw.rest.validator;

import id.tsi.mmw.rest.model.request.AuthenticationRequest;

public class AuthenticationValidator extends BaseValidator {

    public AuthenticationValidator() {
        // Empty Constructor
    }

    public boolean validate(AuthenticationRequest request) {
        return notNull(request)
                && validate(request.getUsername())
                && validate(request.getPassword());
    }
}
