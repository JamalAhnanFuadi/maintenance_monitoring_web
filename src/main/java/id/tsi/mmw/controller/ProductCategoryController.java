package id.tsi.mmw.controller;

import id.tsi.mmw.model.ProductCategory;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductCategoryController extends BaseController {

    public ProductCategoryController() {
        log = getLogger(this.getClass());
    }

    public List<ProductCategory> getProductCategoryList() {
        final String methodName = "getProductCategoryList";
        start(methodName);
        List<ProductCategory> result = new ArrayList<>();

        String sql = "SELECT d.uid, d.display_name, d.description, d.create_dt, d.modify_dt, " +
                " CASE WHEN EXISTS ( SELECT 1  FROM product p  WHERE p.category_uid = d.uid )   THEN TRUE  ELSE FALSE  END AS locked " +
                " FROM product_category d " +
                " ORDER BY d.display_name ASC;";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            result = q.mapToBean(ProductCategory.class).list();
        } catch (Exception e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;
    }

    public ProductCategory getProductCategory(String uid) {
        final String methodName = "getProductCategory";
        start(methodName);
        ProductCategory result = new ProductCategory();

        String sql = "SELECT d.uid, d.display_name, d.description, d.create_dt, d.modify_dt " +
                " FROM product_category d " +
                " WHERE d.uid = :uid;";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("uid", uid);
            result = q.mapToBean(ProductCategory.class).first();
        } catch (Exception e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;
    }

    public boolean addProductCategory(ProductCategory productCategory) {
        final String methodName = "addProductCategory";
        start(methodName);
        boolean result = false;

        String sql = "INSERT INTO product_category " +
                "(uid, display_name, description, create_dt) " +
                "VALUES( LOWER(UUID()), :displayName, :description, CURRENT_TIMESTAMP);";

        try (Handle handle = getHandle(); Update u = handle.createUpdate(sql)) {

            u.bindBean(productCategory);
            result = executeUpdate(u);

        } catch (SQLException e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;

    }

    public boolean updateProductCategory(ProductCategory productCategory) {
        final String methodName = "updateProductCategory";
        start(methodName);
        boolean result = false;

        String sql = "UPDATE product_category " +
                " SET display_name = :displayName, description = :description, modify_dt = CURRENT_TIMESTAMP " +
                " WHERE uid = :uid;";

        try (Handle handle = getHandle(); Update u = handle.createUpdate(sql)) {
            u.bindBean(productCategory);
            result = executeUpdate(u);

        } catch (SQLException e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;

    }

    public boolean validateProductCategory(String uid) {
        final String methodName = "validateProductCategory";
        start(methodName);
        boolean result = false;
        String sql = "SELECT if(COUNT(*)>0,'true','false') " +
                " FROM product_category " +
                " WHERE  uid = :uid;";
        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("uid", uid);
            result = q.mapTo(Boolean.class).one();

        } catch (Exception e) {
            log.error(methodName, e);
        }
        completed(methodName);
        return result;
    }


    public boolean deleteProductCategory(String uid) {
        final String methodName = "deleteProductCategory";
        start(methodName);
        boolean result = false;
        final String sql = "DELETE FROM product_category WHERE uid = :uid";
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bind("uid", uid);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }
}
