package id.tsi.mmw.util.log;

import id.tsi.mmw.util.json.JsonHelper;

public class AppLogger extends BaseLogger {

    public AppLogger(Class<?> clazz) {
        super(clazz);
    }

    // DEBUG METHODS
    public void debug(String methodName, Object obj) {
        log.debug(() -> format(methodName, JsonHelper.toJson(obj)));
    }

    public void debug(String id, String methodName, Object obj) {
        log.debug(() -> format(id, methodName, JsonHelper.toJson(obj)));
    }

    // INFO METHODS
    public void info(String methodName, Object obj) {
        log.info(() -> format(methodName, JsonHelper.toJson(obj)));
    }

    public void info(String id, String methodName, Object obj) {
        log.info(() -> format(id, methodName, JsonHelper.toJson(obj)));
    }

    // WARN METHODS
    public void warn(String methodName, Object obj) {
        log.warn(() -> format(methodName, JsonHelper.toJson(obj)));
    }

    public void warn(String id, String methodName, Object obj) {
        log.warn(() -> format(id, methodName, JsonHelper.toJson(obj)));
    }

    // ERROR METHODS
    public void error(String methodName, Object obj) {
        log.error(() -> format(methodName, JsonHelper.toJson(obj)));
    }

    public void error(String id, String methodName, Object obj) {
        log.error(() -> format(id, methodName, JsonHelper.toJson(obj)));
    }
}
