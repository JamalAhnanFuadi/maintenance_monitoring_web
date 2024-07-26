/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2019 Identiticoders, and individual contributors as indicated by the @author tags. All Rights Reserved
 *
 * The contents of this file are subject to the terms of the Common Development and Distribution License (the License).
 *
 * Everyone is permitted to copy and distribute verbatim copies of this license document, but changing it is not
 * allowed.
 *
 */
package sg.ic.umx.manager;

import java.util.Properties;
import javax.inject.Singleton;
import sg.ic.umx.util.log.AppLogger;
import sg.ic.umx.util.property.Property;

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
