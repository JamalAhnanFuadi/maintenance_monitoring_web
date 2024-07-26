package sg.ic.umx.controller;

import java.util.List;
import java.util.Optional;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;
import sg.ic.umx.model.Server;

public class ServerController extends BaseController {

    public ServerController() {
        log = getLogger(this.getClass());
    }

    public boolean create(Server server) {
        final String methodName = "create";
        start(methodName);
        boolean result = false;
        final String sql =
                "INSERT INTO [server] (id,name,url,username,password) VALUES (:id,:name,:url,:username,:password)";
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bindBean(server);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public boolean update(Server server) {
        final String methodName = "update";
        start(methodName);
        boolean result = false;
        final String sql =
                "UPDATE [server] SET name =:name, url=:url, username=:username, password=:password WHERE id = :id";
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bindBean(server);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public boolean delete(String id) {
        final String methodName = "delete";
        start(methodName);
        boolean result = false;
        final String sql = "DELETE FROM [server] WHERE id = :id";
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bind("id", id);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public List<Server> list() {
        final String methodName = "list";
        start(methodName);
        List<Server> result = null;
        final String sql = "SELECT * FROM [server]";
        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            result = q.mapToBean(Server.class).list();
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public Optional<Server> get(String id) {
        final String methodName = "get";
        start(methodName);
        Optional<Server> result = Optional.empty();
        final String sql = "SELECT * FROM [server] WHERE id = :id";
        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            result = q.bind("id", id).mapToBean(Server.class).findOne();
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public int getCountByName(String name) {
        final String methodName = "getCountByName";
        start(methodName);
        final String sql = "SELECT COUNT(*) FROM [server] WHERE name = :name";
        int result = 0;
        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            result = q.bind("name", name).mapTo(Integer.class).one();
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

}
