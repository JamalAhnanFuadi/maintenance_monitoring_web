package id.tsi.mmw.controller;

import id.tsi.mmw.model.Product;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController extends BaseController {

    public ProductController() {
        log = getLogger(this.getClass());
    }

    public List<Product> getProductList() {
        final String methodName = "getProductList";
        start(methodName);
        List<Product> result = new ArrayList<>();

        String sql = "SELECT p.uid, p.display_name, c.display_name AS principalName, pc.display_name AS categoryName,pb.display_name AS brandName, p.active " +
                " FROM product p " +
                " LEFT JOIN customer c ON c.uid = p.customer_uid " +
                " JOIN product_brand pb ON pb.uid = p.brand_uid " +
                " JOIN product_category pc ON pc.uid =p.category_uid " +
                " ORDER BY p.display_name ASC;";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            result = q.mapToBean(Product.class).list();
        } catch (Exception e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;
    }

    public Product getProduct(String uid) {
        final String methodName = "getProduct";
        start(methodName);
        Product result = new Product();

        String sql = "SELECT p.uid, p.display_name, p.category_uid, pc.display_name AS categoryName, p.brand_uid, pb.display_name AS brandName, p.description, p.active, p.create_dt, p.modify_dt " +
                " FROM product p " +
                " JOIN product_brand pb ON pb.uid = p.brand_uid " +
                " JOIN product_category pc ON pc.uid =p.category_uid " +
                " WHERE p.uid = :uid";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("uid", uid);
            result = q.mapToBean(Product.class).first();
        } catch (Exception e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;
    }

    public boolean addProduct(Product product) {
        final String methodName = "addProduct";
        start(methodName);
        boolean result = false;

        String sql = "INSERT INTO product " +
                "(uid, display_name, category_uid, brand_uid, description, create_dt) " +
                " VALUES( LOWER(UUID()), :displayName, :categoryUid, :brandUid, :description, CURRENT_TIMESTAMP);";

        try (Handle handle = getHandle(); Update u = handle.createUpdate(sql)) {

            u.bindBean(product);
            result = executeUpdate(u);

        } catch (SQLException e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;

    }

    public boolean updateProduct(Product product) {
        final String methodName = "updateProduct";
        start(methodName);
        boolean result = false;

        String sql = "UPDATE product " +
                " SET display_name = :displayName, category_uid = :categoryUid, brand_uid = :brandUid, description = :description, active = :active, modify_dt = CURRENT_TIMESTAMP " +
                " WHERE uid = :uid;";

        try (Handle handle = getHandle(); Update u = handle.createUpdate(sql)) {
            u.bindBean(product);
            result = executeUpdate(u);

        } catch (SQLException e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;

    }

    public boolean validateProduct(String uid) {
        final String methodName = "validateProduct";
        start(methodName);
        boolean result = false;
        String sql = "SELECT if(COUNT(*)>0,'true','false') " +
                " FROM product " +
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


    public boolean deleteProduct(String uid) {
        final String methodName = "deleteProduct";
        start(methodName);
        boolean result = false;
        final String sql = "DELETE FROM product WHERE uid = :uid";
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
