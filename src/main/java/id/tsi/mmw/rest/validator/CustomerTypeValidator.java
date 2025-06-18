package id.tsi.mmw.rest.validator;

import id.tsi.mmw.rest.model.CustomerTypeRequest;

public class CustomerTypeValidator extends PaginationValidator {

    public CustomerTypeValidator() {
        // Empty Constructor
    }

    public boolean create(CustomerTypeRequest request) {
        return notNull(request)
                && validate(request.getDisplayName());
    }
    public boolean update(CustomerTypeRequest request) {
        return notNull(request)
                && validate(request.getUid())
                && validate(request.getDisplayName());
    }
}
