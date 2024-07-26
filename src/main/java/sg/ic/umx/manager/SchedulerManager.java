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

import com.coreoz.wisp.Scheduler;
import com.coreoz.wisp.schedule.Schedules;
import sg.ic.umx.controller.RuleController;
import sg.ic.umx.util.log.AppLogger;
import sg.ic.umx.util.property.Property;

import javax.inject.Singleton;
import java.time.Duration;

@Singleton
public class SchedulerManager {

    private final AppLogger log;

    private final RuleController ruleController;

    private static SchedulerManager instance;

    private SchedulerManager() {

        ruleController = new RuleController();

        log = new AppLogger(this.getClass());
        try {
            Scheduler scheduler = new Scheduler();

            // Set the scheduler based on property file
            // if fixed = true, then fixed daily schedule set in syncjob.time
            // if fixed = false, then testing interval set in test.interval
            if (PropertyManager.getInstance().getBoolProperty(Property.SERVICENOW_SCHEDULER_FIXED)) {
                scheduler.schedule(
                        ruleController::pushServiceNow,
                        Schedules.executeAt(PropertyManager.getInstance().getProperty(Property.SERVICENOW_SYNCJOB_TIME))
                );
            } else {
                scheduler.schedule(
                        ruleController::pushServiceNow,
                        Schedules.fixedDelaySchedule(Duration.ofMinutes(Integer.parseInt(PropertyManager.getInstance().getProperty(Property.SERVICENOW_SCHEDULER_TEST_INTERVAL))))
                );
            }
            log.info("SchedulerManager", "SchedulerManager started and running from a background Job");

        } catch (Exception ex) {
            log.error("", ex);
        }
    }

    public static SchedulerManager getInstance() {
        if (instance == null) {
            instance = new SchedulerManager();
        }
        return instance;
    }

}
