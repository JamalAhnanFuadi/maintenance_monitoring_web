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
package sg.ic.umx.util.property;

public class Property {

    private Property() {}

    public static final String CONFIGURATION_FILE = "application.properties";

    public static final String JDBC_DRIVER = "jdbc.driver";
    public static final String JDBC_URL = "jdbc.url";
    public static final String JDBC_USERNAME = "jdbc.username";
    public static final String JDBC_PASSWORD = "jdbc.password";

    public static final String ADMIN_USERNAME = "administrator.username";
    public static final String ADMIN_PASSWORD = "administrator.password";

    public static final String IDG_API_PATH = "idg.api.path";

    public static final String UMX_PROXY_REQUIRED = "umx.proxy.required";
    public static final String UMX_PROXY_URL = "umx.proxy.url";
    public static final String UMX_PROXY_PORT = "umx.proxy.port";

    public static final String SERVICENOW_INSERT_URL = "servicenow.insert.url";
    public static final String SERVICENOW_UPDATE_URL = "servicenow.update.url";

    public static final String SERVICENOW_USERNAME = "servicenow.username";
    public static final String SERVICENOW_PASSWORD = "servicenow.password";

    public static final String SERVICENOW_SCHEDULER_FIXED = "servicenow.scheduler.fixed";
    public static final String SERVICENOW_SCHEDULER_TEST_INTERVAL = "servicenow.scheduler.test.interval";
    public static final String SERVICENOW_SYNCJOB_TIME = "servicenow.syncjob.time";

    public static final String SERVICENOW_APPLICATION_PUSH = "servicenow.application.name";

}
