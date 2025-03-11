package id.tsi.mmw.controller.audit;

import id.tsi.mmw.controller.BaseController;
import id.tsi.mmw.controller.Controller;
import id.tsi.mmw.model.Authentication;
import id.tsi.mmw.model.audit.AuditAuthentication;
import id.tsi.mmw.property.Property;
import id.tsi.mmw.util.json.JsonHelper;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;

import java.sql.SQLException;

@Controller
public class AuditAuthenticationController extends BaseController {

    public AuditAuthenticationController() {
        log = getLogger(this.getClass());
    }

    public void insertAuditAuthentication(AuditAuthentication audit) {
        final String methodName = "insertAuditAuthentication";
        start(methodName);

        boolean auditEnabled = getBoolProperty(Property.AUDIT_ENABLE);
        if(auditEnabled) {

            final String sql = "INSERT INTO audit_authentication " +
                    "(uid, tracking_id, `user`, event, application, `result`, message, created_dt) " +
                    " VALUES ( LOWER(UUID()), :trackingId, :user, :event, :application, :result, :message, :createdDt);";

            try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
                u.bindBean(audit);
                executeUpdate(u);

            } catch (Exception ex) {
                log.error(methodName, ex);
            }
        } else {
            log.debug(methodName, JsonHelper.toJson(audit));
        }

        completed(methodName);
    }
}
