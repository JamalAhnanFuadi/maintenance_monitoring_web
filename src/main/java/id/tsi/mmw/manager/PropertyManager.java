package id.tsi.mmw.manager;

import java.util.Properties;
import javax.inject.Singleton;
import id.tsi.mmw.util.log.AppLogger;
import id.tsi.mmw.property.Property;

@Singleton
public class PropertyManager {

    private final AppLogger log;

    private static PropertyManager instance;

    private Properties prop;

    private PropertyManager() {
        log = new AppLogger(this.getClass());
        try {
            prop = new Properties();
            prop.load(PropertyManager.class.getClassLoader().getResourceAsStream(Property.CONFIGURATION_FILE));

        } catch (Exception ex) {
            log.error("", ex);
        }
    }

    public static PropertyManager getInstance() {
        if (instance == null) {
            instance = new PropertyManager();
        }
        return instance;
    }

    public String getProperty(String key) {
        return prop.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return prop.getProperty(key, defaultValue);
    }

    public boolean getBoolProperty(String key) {
        return Boolean.parseBoolean(prop.getProperty(key, "false"));

    }

}
