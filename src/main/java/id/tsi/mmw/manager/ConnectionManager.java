package id.tsi.mmw.manager;

import javax.inject.Singleton;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import id.tsi.mmw.util.log.AppLogger;
import id.tsi.mmw.property.Property;

import java.sql.Connection;
import java.sql.SQLException;

@Singleton
public class ConnectionManager extends BaseManager {

    private static ConnectionManager instance;

    private HikariDataSource dataSource;

    /**
     * Private constructor for the ConnectionManager class
     */
    private ConnectionManager() {
        final String methodName = "Constructor";

        // Initialize the logger for the ConnectionManager class
        log = new AppLogger(ConnectionManager.class);

        // Log a debug message indicating the start of the constructor
        log.debug(methodName, "Start");

        // Initialize a new HikariConfig object to configure the connection
        HikariConfig config = new HikariConfig();

        // Log an info message indicating the initiation of the connection to the specified URL
        log.info("Initiating Connection to : " + getProp(Property.DB_URL));

        // Retrieve the username, password, driver class name, URL, and pool size from properties
        final String USERNAME = getProp(Property.DB_USERNAME);
        final String PASSWORD = EncryptionManager.getInstance().decrypt(getProp(Property.DB_PASSWORD));
        final String DATASOURCE_CLASS_NAME = getProp(Property.DB_DRIVER_CLASSNAME);
        final String URL = getProp(Property.DB_URL);
        final int POOL_SIZE = getIntProp(Property.DB_POOL_SIZE);

        // Set the username and password in the HikariConfig object
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);

        // Set the driver class name and URL in the HikariConfig object
        config.setDriverClassName(DATASOURCE_CLASS_NAME);
        config.setJdbcUrl(URL);

        // Set the maximum pool size in the HikariConfig object
        config.setMaximumPoolSize(POOL_SIZE);

        // Create a new HikariDataSource object with the configured settings
        dataSource = new HikariDataSource(config);

        // Call the completed method to indicate the completion of the constructor
        completed(methodName);
    }

    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private String getProp(String key) {
        return PropertyManager.getInstance().getProperty(key);
    }

    private int getIntProp(String key) {
        return PropertyManager.getInstance().getIntProperty(key);
    }

    public void shutdown() {
        log.info("shutdown", "Stopping Data Source");
        dataSource.close();
        dataSource = null;
        log.info("shutdown", "Data Sources Stopped");

    }
}
