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

    private ConnectionManager() {
        final String methodName = "Constructor";
        log = new AppLogger(ConnectionManager.class);
        log.debug(methodName, "Start");
        HikariConfig config = new HikariConfig();

        log.info("Initiating Connection to : " + getProp(Property.DB_URL));

        final String USERNAME = getProp(Property.DB_USERNAME);
        final String PASSWORD = EncryptionManager.getInstance().decrypt(getProp(Property.DB_PASSWORD));
        final String DATASOURCE_CLASS_NAME = getProp(Property.DB_DRIVER_CLASSNAME);
        final String URL = getProp(Property.DB_URL);
        final int POOL_SIZE = getIntProp(Property.DB_POOL_SIZE);

        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);

        config.setDriverClassName(DATASOURCE_CLASS_NAME);
        config.setJdbcUrl(URL);

        // Pool Size
        config.setMaximumPoolSize(POOL_SIZE);
        dataSource = new HikariDataSource(config);
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
