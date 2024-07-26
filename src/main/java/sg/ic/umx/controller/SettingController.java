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
import sg.ic.umx.model.Setting;

public class SettingController extends BaseController {

    public SettingController() {
        log = getLogger(this.getClass());
    }

    public Setting get(String name) {
        final String methodName = "get";
        start(methodName);
        final String sql = "SELECT * FROM [setting] WHERE name =:name ORDER BY id";
        Optional<Setting> result = Optional.empty();
        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            result = q.bind("name", name).mapToBean(Setting.class).findFirst();
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result.isPresent() ? result.get() : null;
    }

    public boolean update(List<Setting> settingList) {
        final String methodName = "update";
        start(methodName);
        final String sql = "UPDATE [setting] SET name=:name, value=:value WHERE id=:id";
        boolean result = false;
        try (Handle h = getHandle()) {
            PreparedBatch batch = h.prepareBatch(sql);
            settingList.forEach(setting -> batch.bindBean(setting).add());
            result = executeBatch(batch);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public List<Setting> list() {
        final String methodName = "list";
        start(methodName);
        final String sql = "SELECT * FROM [setting] ORDER BY sortOrder, id";
        List<Setting> settingList = new ArrayList<>();
        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            settingList = q.mapToBean(Setting.class).list();
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return settingList;
    }


}
