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

}
