package id.tsi.mmw.rest.validator;

import id.tsi.mmw.rest.model.request.OTPRequest;

public class OTPValidator extends BaseValidator{

    public OTPValidator() {
        // Empty Constructor
    }

    public boolean validateRequestOtp (OTPRequest request) {
        return notNull(request)
                && validate(request.getEmail());
    }

}
