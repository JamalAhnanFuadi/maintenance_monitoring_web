package id.tsi.mmw.rest.validator;

import id.tsi.mmw.rest.model.request.OTPRequest;

public class OTPValidator extends BaseValidator{

    public OTPValidator() {
        // Empty Constructor
    }

    public boolean validateOtpRequest(OTPRequest request) {
        return notNull(request)
                && validate(request.getEmail());
    }
    public boolean validateOtpValidate (OTPRequest request) {
        return notNull(request)
                && validate(request.getEmail())
                && validate(request.getOtpCode());
    }

}
