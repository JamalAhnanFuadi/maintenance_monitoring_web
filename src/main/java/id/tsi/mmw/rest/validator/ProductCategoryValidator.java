package id.tsi.mmw.rest.validator;

import id.tsi.mmw.rest.model.request.ProductCategoryRequest;

public class ProductCategoryValidator extends PaginationValidator {

    public ProductCategoryValidator() {
        // Empty Constructor
    }

    public boolean create(ProductCategoryRequest request) {
        return notNull(request)
                && validate(request.getDisplayName());
    }
    public boolean update(ProductCategoryRequest request) {
        return notNull(request)
                && validate(request.getUid())
                && validate(request.getDisplayName());
    }
}
