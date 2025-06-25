package id.tsi.mmw.rest.validator;

import id.tsi.mmw.rest.model.request.ServiceLevelRequest;

public class ServiceLevelValidator extends PaginationValidator {

    public ServiceLevelValidator() {
        // Empty Constructor
    }

    public boolean create(ServiceLevelRequest request) {
        return notNull(request)
                && validate(request.getDisplayName());
    }
    public boolean update(ServiceLevelRequest request) {
        return notNull(request)
                && validate(request.getUid())
                && validate(request.getDisplayName());
    }
}
