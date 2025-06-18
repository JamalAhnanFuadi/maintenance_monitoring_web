package id.tsi.mmw.controller;

import id.tsi.mmw.model.EmailTemplate;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EmailTemplateController extends BaseController {

    public EmailTemplateController() {
        log = getLogger(this.getClass());
    }

    public List<EmailTemplate> getEmailTemplateList() {
        final String methodName = "getEmailTemplateList";
        start(methodName);
        List<EmailTemplate> result = new ArrayList<>();

        String sql = "SELECT et.uid, et.name, et.subject, et.modify_dt " +
                " FROM email_template et " +
                " ORDER BY et.name ASC;";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            result = q.mapToBean(EmailTemplate.class).list();
        } catch (Exception e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;
    }

    public EmailTemplate getEmailTemplate(String uid) {
        final String methodName = "getEmailTemplate";
        start(methodName);
        EmailTemplate result = new EmailTemplate();

        String sql = "SELECT et.uid, et.name, et.subject, et.html_content, et.create_dt, et.modify_dt " +
                " FROM email_template et " +
                " WHERE et.uid = :uid;";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("uid", uid);
            result = q.mapToBean(EmailTemplate.class).first();
        } catch (Exception e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;
    }

    public boolean addEmailTemplate(EmailTemplate emailTemplate) {
        final String methodName = "addEmailTemplate";
        start(methodName);
        boolean result = false;

        String sql = "INSERT INTO email_template " +
                "(uid, name, subject, html_content, create_dt) " +
                "VALUES( LOWER(UUID()), :name, :subject, :htmlContent, CURRENT_TIMESTAMP);";

        try (Handle handle = getHandle(); Update u = handle.createUpdate(sql)) {

            u.bindBean(emailTemplate);
            result = executeUpdate(u);

        } catch (SQLException e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;

    }

    public boolean updateEmailTemplate(EmailTemplate emailTemplate) {
        final String methodName = "updateEmailTemplate";
        start(methodName);
        boolean result = false;

        String sql = "UPDATE email_template " +
                " SET name = :name, subject = :subject, html_content = :htmlContent, modify_dt = CURRENT_TIMESTAMP " +
                " WHERE uid = :uid;";

        try (Handle handle = getHandle(); Update u = handle.createUpdate(sql)) {
            u.bindBean(emailTemplate);
            result = executeUpdate(u);

        } catch (SQLException e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;

    }

    public boolean validateEmailTemplate(String uid) {
        final String methodName = "validateEmailTemplate";
        start(methodName);
        boolean result = false;
        String sql = "SELECT if(COUNT(*)>0,'true','false') " +
                " FROM email_template " +
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


    public boolean deleteEmailTemplate(String uid) {
        final String methodName = "deleteEmailTemplate";
        start(methodName);
        boolean result = false;
        final String sql = "DELETE FROM email_template WHERE uid = :uid";
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
