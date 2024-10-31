package id.tsi.mmw.controller;

import id.tsi.mmw.model.OTP;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;

@Controller
public class OTPController extends BaseController{

    public OTPController() {
        log = getLogger(this.getClass());
    }

    public boolean insertOtp(OTP otp) {
        final String methodName = "insertOtp";
        start(methodName);
        boolean result = false;

        String sql = "INSERT INTO otp " +
                "(`user`, otp_code, retry_count, expiry_dt, create_dt) " +
                "VALUES( :user, :otpCode, :retryCount, :expiryDt, :createDt);";

        log.debug(methodName, "SQL : " + sql);
        try (Handle handle = getHandle(); Update update = handle.createUpdate(sql)) {
            update.bindBean(otp);
            result = executeUpdate(update);
        } catch (Exception e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;
    }

    public OTP validateOtp(String otpCode, String email) {
        final String methodName = "validateUserUid";
        start(methodName);
        OTP otp = null;
        String sql = "SELECT `user`, otp_code, expiry_dt, " +
                " CASE WHEN expiry_dt > NOW() THEN 'Valid' " +
                "   ELSE 'Expired' " +
                "   END AS otp_status " +
                " FROM otp " +
                " WHERE `user` = :email AND `otp_code` = :otpCode;";
        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("email", email);
            q.bind("otpCode", otpCode);
            otp = q.mapToBean(OTP.class).first();

        } catch (Exception e) {
            log.error(methodName, e);
        }
        completed(methodName);
        return otp;
    }

    public boolean deleteOtp(String user) {
        final String methodName = "insertOtp";
        start(methodName);
        boolean result = false;

        String sql = "DELETE FROM otp " +
                " WHERE `user`= :user;";

        log.debug(methodName, "SQL : " + sql);
        try (Handle handle = getHandle(); Update update = handle.createUpdate(sql)) {
            update.bind("user", user);
            result = executeUpdate(update);
        } catch (Exception e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;
    }
}
