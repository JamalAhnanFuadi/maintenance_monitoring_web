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
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.PreparedBatch;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;
import sg.ic.umx.model.Dispensation;

public class DispensationController extends BaseController {

    public DispensationController() {
        log = getLogger(this.getClass());
    }

    public boolean create(Dispensation account) {
        final String methodName = "create";
        start(methodName);
        final String sql = "INSERT INTO dispensation (applicationId, name) values (:applicationId, :name)";
        boolean result = false;
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {

            u.bindBean(account);
            result = executeUpdate(u);

        } catch (Exception ex) {
            log.error(methodName, ex);
        }

        completed(methodName);
        return result;
    }

    public boolean create(List<Dispensation> accountList) {
        final String methodName = "create";
        start(methodName);
        final String sql = "INSERT INTO dispensation (applicationId, name) values (:applicationId, :name)";
        boolean result = false;
        try (Handle h = getHandle()) {
            PreparedBatch batch = h.prepareBatch(sql);
            accountList.forEach(account -> batch.bindBean(account).add());
            result = executeBatch(batch);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }

        completed(methodName);
        return result;
    }


    public boolean delete(long id) {
        final String methodName = "delete";
        start(methodName);
        final String sql = "DELETE FROM dispensation WHERE id = :id";
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

    public boolean deleteByApplicationId(long applicationId) {
        final String methodName = "deleteByApplicationId";
        start(methodName);
        final String sql = "DELETE FROM dispensation WHERE applicationId = :applicationId";
        boolean result = false;
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bind("applicationId", applicationId);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }

        completed(methodName);
        return result;
    }

    public List<Dispensation> listByApplicationId(long applicationId) {
        final String methodName = "listByApplicationId";
        start(methodName);
        final String sql = "SELECT * FROM dispensation WHERE applicationId = :applicationId ORDER BY name, id";
        List<Dispensation> result = new ArrayList<>();
        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            result = q.bind("applicationId", applicationId).mapToBean(Dispensation.class).list();
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }
}
