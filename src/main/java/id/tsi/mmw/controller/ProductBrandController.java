package id.tsi.mmw.controller;

import id.tsi.mmw.model.ProductBrand;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductBrandController extends BaseController {

    public ProductBrandController() {
        log = getLogger(this.getClass());
    }

    public List<ProductBrand> getProductBrandList() {
        final String methodName = "getProductBrandList";
        start(methodName);
        List<ProductBrand> result = new ArrayList<>();

        String sql = "SELECT d.uid, d.display_name, d.description, d.create_dt, d.modify_dt, " +
                " CASE WHEN EXISTS ( SELECT 1  FROM product p  WHERE p.brand_uid = d.uid )   THEN TRUE  ELSE FALSE  END AS locked " +
                " FROM product_brand d " +
                " ORDER BY d.display_name ASC;";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            result = q.mapToBean(ProductBrand.class).list();
        } catch (Exception e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;
    }

    public ProductBrand getProductBrand(String uid) {
        final String methodName = "getProductBrand";
        start(methodName);
        ProductBrand result = new ProductBrand();

        String sql = "SELECT d.uid, d.display_name, d.description, d.create_dt, d.modify_dt " +
                " FROM product_brand d " +
                " WHERE d.uid = :uid;";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("uid", uid);
            result = q.mapToBean(ProductBrand.class).first();
        } catch (Exception e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;
    }

    public boolean addProductBrand(ProductBrand productBrand) {
        final String methodName = "addProductBrand";
        start(methodName);
        boolean result = false;

        String sql = "INSERT INTO product_brand " +
                "(uid, display_name, description, create_dt) " +
                "VALUES( LOWER(UUID()), :displayName, :description, CURRENT_TIMESTAMP);";

        try (Handle handle = getHandle(); Update u = handle.createUpdate(sql)) {

            u.bindBean(productBrand);
            result = executeUpdate(u);

        } catch (SQLException e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;

    }

    public boolean updateProductBrand(ProductBrand productBrand) {
        final String methodName = "updateProductBrand";
        start(methodName);
        boolean result = false;

        String sql = "UPDATE product_brand " +
                " SET display_name = :displayName, description = :description, modify_dt = CURRENT_TIMESTAMP " +
                " WHERE uid = :uid;";

        try (Handle handle = getHandle(); Update u = handle.createUpdate(sql)) {
            u.bindBean(productBrand);
            result = executeUpdate(u);

        } catch (SQLException e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;

    }

    public boolean validateProductBrand(String uid) {
        final String methodName = "validateProductBrand";
        start(methodName);
        boolean result = false;
        String sql = "SELECT if(COUNT(*)>0,'true','false') " +
                " FROM product_brand " +
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


    public boolean deleteProductBrand(String uid) {
        final String methodName = "deleteProductBrand";
        start(methodName);
        boolean result = false;
        final String sql = "DELETE FROM product_brand WHERE uid = :uid";
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
