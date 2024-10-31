package id.tsi.mmw.rest.validator;

import id.tsi.mmw.rest.model.request.EmailValidateRequest;
import id.tsi.mmw.rest.model.request.PaginationRequest;

public class PaginationValidator extends BaseValidator {

    public boolean validate(PaginationRequest request) {
        return notNull(request) && request.getPageNumber() > 0 && request.getPageSize() > 0
                && validate(request.getOrderBy()) && validate(request.getSortOrder());
    }
}
