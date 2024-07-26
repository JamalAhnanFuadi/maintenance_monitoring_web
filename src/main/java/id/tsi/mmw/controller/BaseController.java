package id.tsi.mmw.controller;

import id.tsi.mmw.manager.ConnectionManager;
import id.tsi.mmw.manager.PropertyManager;
import id.tsi.mmw.util.log.AppLogger;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.PreparedBatch;
import org.jdbi.v3.core.statement.Update;

import java.sql.SQLException;

public class BaseController {

    protected AppLogger log;

    public BaseController() {
        // Empty Constructor
    }
    // Logging
    protected AppLogger getLogger(Class<?> clazz) {
        return new AppLogger(clazz);
    }
    protected void start(String methodName) {
        log.debug(methodName, "start");
    }

    protected void completed(String methodName) {
        log.debug(methodName, "completed");
    }

    // Properties Manager
    protected String getProperty(String key) {
        return PropertyManager.getInstance().getProperty(key);
    }

    protected boolean getBoolProperty(String key) {
        return PropertyManager.getInstance().getBoolProperty(key);
    }

    // Database
    protected Handle getHandle() throws SQLException {
        return Jdbi.open(ConnectionManager.getInstance().getConnection());
    }

    protected Handle getHandle(RowMapper<?>... rowMappers) throws SQLException {
        Handle h = getHandle();

        if (rowMappers != null && rowMappers.length > 0) {
            for (RowMapper<?> mapper : rowMappers) {
                h.registerRowMapper(mapper);
            }
        }
        return h;
    }

    protected boolean executeUpdate(Update update) {
        return update.execute() > 0;
    }

    protected boolean executeBatch(PreparedBatch batch) {
        int[] resultArr = batch.execute();

        for (int result : resultArr) {
            if (result < 0) {
                return false;
            }
        }
        return true;
    }
}
