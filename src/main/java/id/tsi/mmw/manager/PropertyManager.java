package id.tsi.mmw.manager;

import java.util.Properties;
import javax.inject.Singleton;
import id.tsi.mmw.util.log.AppLogger;
import id.tsi.mmw.property.Property;

@Singleton
public class PropertyManager extends BaseManager{

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

    public int getIntProperty(String key) {
        return getIntProperty(key, 0);
    }

    public int getIntProperty(String key, int defaultValue) {
        return Integer.parseInt(getProperty(key, String.valueOf(defaultValue)));
    }

}
