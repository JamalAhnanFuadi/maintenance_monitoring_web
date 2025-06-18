package id.tsi.mmw.property;

public class Property {

    private Property() {}

    public static final String CONFIGURATION_FILE = "application.properties";

    public static final String DB_DRIVER_CLASSNAME = "db.driver";
    public static final String DB_URL = "db.url";
    public static final String DB_USERNAME = "db.username";
    public static final String DB_PASSWORD = "db.password";
    public static final String DB_POOL_SIZE = "db.pool-size";

    public static final String AUDIT_ENABLE = "audit.enable";


    public static final String ENCRYPTION_SALT_LENGTH = "encryption.salt.length";
    public static final String STAFF_DEFAULT_PASSWORD = "staff.default.password";

    public static final String MAIL_SMTP_SENDER = "mail.sender";


    public static final String MAIL_MS_SERVER = "mail.ms.server";
    public static final String MAIL_MS_SEND_EMAIL_API = "mail.ms.send-email.api";
    public static final String MAIL_MS_KEY_NAME = "mail.ms.key-name";
    public static final String MAIL_MS_API_KEY = "mail.ms.api-key";

    public static final String OTP_TEST_ENABLE = "otp.test.enable";
    public static final String OTP_TEST_CODE = "otp.test.code";
    public static final String OTP_EXPIRY = "otp.expiry";
    public static final String OTP_RETRY_COUNT = "otp.retry.count";
    public static final String OTP_LINK_FORMAT = "otp.link.format";

    public static final String PASSWORD_RESET_LINK_FORMAT = "password.reset.link.format";
}
