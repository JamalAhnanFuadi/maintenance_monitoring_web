package id.tsi.mmw.rest.validator;

import id.tsi.mmw.rest.model.request.EmailValidateRequest;
import id.tsi.mmw.rest.model.request.PasswordRequest;

public class PasswordValidator extends BaseValidator{

    public PasswordValidator() {
        // Empty Constructor
    }

    public boolean validate (PasswordRequest request) {
        return notNull(request)
                && validate(request.getEmail())
                && validate(request.getPassword());
    }


    public boolean validate(EmailValidateRequest request) {
        return notNull(request)
                && validate(request.getEmail());
    }
}
