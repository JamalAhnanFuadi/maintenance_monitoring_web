package id.tsi.mmw.property;

public class Property {

    private Property() {}

    public static final String CONFIGURATION_FILE = "application.properties";

    public static final String DB_DRIVER_CLASSNAME = "db.driver";
    public static final String DB_URL = "db.url";
    public static final String DB_USERNAME = "db.username";
    public static final String DB_PASSWORD = "db.password";
    public static final String DB_POOL_SIZE = "db.pool-size";

    public static final String JWT_SECRET = "jwt.secret";
    public static final String JWT_EXPIRATION = "jwt.expiration";

    public static final String ENCRYPTION_SALT_LENGTH = "encryption.salt.length";
    public static final String USER_DEFAULT_PASSWORD = "user.default.password";

    public static final String MAIL_SMTP_HOST = "mail.smtp.host";
    public static final String MAIL_SMTP_PORT = "mail.smtp.port";
    public static final String MAIL_SMTP_SENDER = "mail.smtp.sender";
    public static final String MAIL_SMTP_USERNAME = "mail.smtp.username";
    public static final String MAIL_SMTP_PASSWORD = "mail.smtp.password";
    public static final String MAIL_SMTP_AUTH_REQUIRED = "mail.smtp.auth.required";
    public static final String MAIL_SMTP_STARTTLS = "mail.smtp.starttls.enable";
    public static final String MAIL_SMTP_SOCKET_PORT = "mail.smtp.socketFactory.port";
    public static final String MAIL_SMTP_SOCKET_CLASS = "mail.smtp.socketFactory.class";

}
