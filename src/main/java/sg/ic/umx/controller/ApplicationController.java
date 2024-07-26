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
package sg.ic.umx.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.PreparedBatch;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;
import sg.ic.umx.controller.rowmapper.ApplicationRowMapper;
import sg.ic.umx.model.Application;

public class ApplicationController extends BaseController {

    private final ApplicationRowMapper applicationRowMapper;

    public ApplicationController() {
        log = getLogger(this.getClass());
        applicationRowMapper = new ApplicationRowMapper();
    }

    public boolean create(Application application) {
        final String methodName = "create";
        start(methodName);
        final String sql =
                "INSERT INTO [application] (name,serverId, configurationName, recipientList, attributeList, mailSubject, mailBody, status) "
                        + "VALUES (:a.name,:s.id, :a.configurationName, :a.recipientList, :a.attributeList,:a.mailSubject,:a.mailBody,:a.status)";
        boolean result = false;
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bindBean("a", application).bindBean("s", application.getServer());
            result = executeUpdate(u);

        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public boolean update(Application application) {
        final String methodName = "update";
        start(methodName);
        final String sql =
                "UPDATE [application] set name=:a.name, serverId=:s.id, configurationName=:a.configurationName, recipientList = :a.recipientList, "
                        + " mailSubject =:a.mailSubject, mailBody = :a.mailBody WHERE id = :a.id";
        boolean result = false;
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bindBean("a", application).bindBean("s", application.getServer());
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public boolean delete(long id) {
        final String methodName = "delete";
        start(methodName);
        final String sql = "DELETE FROM [application] WHERE id = :id";
        boolean result = false;
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bind("id", id);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public Application get(long id) {
        final String methodName = "get";
        start(methodName);
        final String sql = "SELECT * FROM [application] WHERE id = :id";
        Optional<Application> result = Optional.empty();
        try (Handle h = getHandle(applicationRowMapper); Query q = h.createQuery(sql)) {
            result = q.bind("id", id).mapTo(Application.class).findFirst();
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result.isPresent() ? result.get() : null;
    }

    public List<Application> list() {
        final String methodName = "list";
        start(methodName);
        final String sql = "SELECT * FROM [application] ORDER BY status asc, name asc";
        List<Application> result = new ArrayList<>();
        try (Handle h = getHandle(applicationRowMapper); Query q = h.createQuery(sql)) {
            result = q.mapTo(Application.class).list();
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public List<Application> listByStatus(boolean status) {
        final String methodName = "listByStatus";
        start(methodName);
        final String sql = "SELECT * FROM [application] WHERE status =:status ORDER BY name";
        List<Application> result = new ArrayList<>();
        try (Handle h = getHandle(applicationRowMapper); Query q = h.createQuery(sql)) {
            result = q.bind("status", status).mapTo(Application.class).list();
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public List<Application> listByServerId(String serverId) {
        final String methodName = "listByServerId";
        start(methodName);
        final String sql = "SELECT * FROM [application] WHERE serverId = :serverId ORDER BY name";
        List<Application> result = new ArrayList<>();
        try (Handle h = getHandle(applicationRowMapper); Query q = h.createQuery(sql)) {
            result = q.bind("serverId", serverId).mapTo(Application.class).list();
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public int getCountByName(String name) {
        final String methodName = "getCountByName";
        start(methodName);
        final String sql = "SELECT COUNT(*) FROM [application] WHERE name = :name";
        int result = 0;
        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            result = q.bind("name", name).mapTo(Integer.class).one();
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public boolean updateApplicationStatus(List<Long> appIdList, boolean status) {
        final String methodName = "updateApplicationStatus";
        start(methodName);
        final String sql = "UPDATE [application] SET status = :status WHERE id = :id";
        boolean result = false;
        try (Handle h = getHandle(); PreparedBatch batch = h.prepareBatch(sql)) {
            if (!appIdList.isEmpty()) {
                for (long id : appIdList) {
                    batch.bind("status", status).bind("id", id).add();
                }
                result = executeBatch(batch);
            } else {
                result = true;
            }
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;


    }

    public int getApplicationIdByName(String name) {
        final String methodName = "getApplicationIdByName";
        start(methodName);

        final String sql = "SELECT id FROM [application] WHERE name = :name";
        int result = 0;
        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            result = q.bind("name", name).mapTo(Integer.class).one();
        } catch (Exception ex) {
            log.error(methodName, ex);
        }

        log.debug(methodName, "App ID for App Name " + name + " : " + result);

        completed(methodName);
        return result;
    }
}
