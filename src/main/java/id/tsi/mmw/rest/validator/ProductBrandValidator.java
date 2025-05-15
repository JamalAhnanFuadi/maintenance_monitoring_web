package id.tsi.mmw.rest.validator;

import id.tsi.mmw.rest.model.request.ProductBrandRequest;

public class ProductBrandValidator extends PaginationValidator {

    public ProductBrandValidator() {
        // Empty Constructor
    }

    public boolean create(ProductBrandRequest request) {
        return notNull(request)
                && validate(request.getDisplayName());
    }
    public boolean update(ProductBrandRequest request) {
        return notNull(request)
                && validate(request.getUid())
                && validate(request.getDisplayName());
    }
}
