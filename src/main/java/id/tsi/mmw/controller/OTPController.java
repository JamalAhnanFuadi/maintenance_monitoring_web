package id.tsi.mmw.controller;

import id.tsi.mmw.model.OTP;
import org.jdbi.v3.core.Handle;
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
}
