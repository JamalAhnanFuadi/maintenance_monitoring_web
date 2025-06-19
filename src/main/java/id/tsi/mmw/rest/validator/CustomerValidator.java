package id.tsi.mmw.rest.validator;

import id.tsi.mmw.rest.model.request.CustomerRequest;

public class CustomerValidator extends PaginationValidator {

    public CustomerValidator() {
        // Empty Constructor
    }

    public boolean create(CustomerRequest request) {
        return notNull(request)
                && validate(request.getDisplayName());
    }
    public boolean update(CustomerRequest request) {
        return notNull(request)
                && validate(request.getUid())
                && validate(request.getDisplayName());
    }
}
