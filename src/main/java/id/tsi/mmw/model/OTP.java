package id.tsi.mmw.model;

public class OTP {

    private String user;
    private String otpCode;
    private int retryCount;
    private String expiryDt;
    private String createDt;
    private String otpStatus;

    public OTP() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public String getExpiryDt() {
        return expiryDt;
    }

    public void setExpiryDt(String expiryDt) {
        this.expiryDt = expiryDt;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }

    public String getOtpStatus() {
        return otpStatus;
    }

    public void setOtpStatus(String otpStatus) {
        this.otpStatus = otpStatus;
    }
}
