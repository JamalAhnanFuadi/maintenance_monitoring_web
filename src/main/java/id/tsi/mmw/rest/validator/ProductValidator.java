package id.tsi.mmw.rest.validator;

import id.tsi.mmw.rest.model.request.ProductRequest;

public class ProductValidator extends PaginationValidator {

    public ProductValidator() {
        // Empty Constructor
    }

    public boolean create(ProductRequest request) {
        return notNull(request)
                && validate(request.getDisplayName())
                && validate(request.getCategoryUid())
                && validate(request.getBrandUid());
    }
    public boolean update(ProductRequest request) {
        return notNull(request)
                && validate(request.getUid())
                && validate(request.getDisplayName())
                && validate(request.getCategoryUid())
                && validate(request.getBrandUid());
    }
}
