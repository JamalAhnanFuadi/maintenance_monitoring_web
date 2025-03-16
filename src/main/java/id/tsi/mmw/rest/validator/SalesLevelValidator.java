package id.tsi.mmw.rest.validator;

import id.tsi.mmw.rest.model.request.SalesLevelRequest;

public class SalesLevelValidator extends PaginationValidator {

    public SalesLevelValidator() {
        // Empty Constructor
    }

    public boolean create(SalesLevelRequest request) {
        return notNull(request)
                && validate(request.getDisplayName());
    }
    public boolean update(SalesLevelRequest request) {
        return notNull(request)
                && validate(request.getUid())
                && validate(request.getDisplayName());
    }
}
