package sg.ic.umx.controller;

import java.util.*;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.mapper.JoinRow;
import org.jdbi.v3.core.mapper.JoinRowMapper;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;
import org.jdbi.v3.core.statement.PreparedBatch;
import org.jdbi.v3.core.statement.Update;
import sg.ic.umx.model.ApplicationRole;
import sg.ic.umx.model.BusinessRole;

public class BusinessRoleController extends BaseController {

    public BusinessRoleController() {
        log = getLogger(this.getClass());
    }

    public boolean create(List<BusinessRole> bizRoleList) {
        final String methodName = "create";
        start(methodName);

        final String roleQuery = "INSERT INTO [business_role] (id,name,hrRole,description) VALUES (:id,:name,:hrRole,:description)";
        final String roleItemQuery =
                "INSERT INTO [business_role_item] (roleId,application,role) VALUES (:roleId,:application,:role)";

        boolean result = false;

        try (Handle h = getHandle()) {

            PreparedBatch roleBatch = h.prepareBatch(roleQuery);
            PreparedBatch roleItemBatch = h.prepareBatch(roleItemQuery);


            for (BusinessRole bizRole : bizRoleList) {
                roleBatch.bindBean(bizRole).add();

                for (ApplicationRole appRole : bizRole.getRoleList()) {
                    roleItemBatch.bind("roleId", bizRole.getId()).bindBean(appRole).add();
                }
            }

            result = executeBatch(roleBatch) && executeBatch(roleItemBatch);

        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public List<BusinessRole> list() {
        final String methodName = "list";
        start(methodName);

        String sql =
                "SELECT b.id AS 'bid', b.name AS 'bname',  b.hrRole AS 'bhrRole', b.description AS 'bdescription',b.createdDt AS 'bcreatedDt',"
                        + " i.roleId AS 'iid', i.application AS 'iapplication', i.role AS 'irole' "
                        + "FROM business_role b, business_role_item i  " + "WHERE b.id = i.roleId";

        Map<String, BusinessRole> roleMap = new HashMap<>();

        ArrayList<BusinessRole> roleList = new ArrayList<>();

        try (Handle h = getHandle()) {
            h.registerRowMapper(BeanMapper.factory(BusinessRole.class, "b"));
            h.registerRowMapper(BeanMapper.factory(ApplicationRole.class, "i"));
            h.registerRowMapper(JoinRowMapper.forTypes(BusinessRole.class, ApplicationRole.class));

            // Process output rows
            h.select(sql).mapTo(JoinRow.class).list().forEach(row -> {

                BusinessRole bRole = row.get(BusinessRole.class);
                ApplicationRole aRole = row.get(ApplicationRole.class);

                if (roleMap.containsKey(bRole.getId())) {
                    roleMap.get(bRole.getId()).addRole(aRole);

                } else {
                    bRole.addRole(aRole);
                    roleMap.put(bRole.getId(), bRole);
                }
            });

            roleList = new ArrayList<BusinessRole>(roleMap.values());


        } catch (Exception ex) {
            log.error(methodName, ex);
        }

        completed(methodName);
        return roleList;
    }

    public boolean clear() {
        final String methodName = "clear";
        start(methodName);
        boolean result = false;
        final String roleQuery = "DELETE FROM [business_role]";
        final String roleItemQuery = "DELETE FROM [business_role_item]";

        try (Handle h = getHandle()) {

            result = h.inTransaction(handle -> {
                handle.begin();

                Update deleteBizRole = h.createUpdate(roleQuery);

                Update deleteBizRoleItem = h.createUpdate(roleItemQuery);

                boolean deleteResult = executeUpdate(deleteBizRole) && executeUpdate(deleteBizRoleItem);

                if (deleteResult) {
                    handle.commit();
                } else {
                    handle.rollback();
                }
                return deleteResult;
            });

        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

}
