package sg.ic.umx.controller.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import com.fasterxml.jackson.core.type.TypeReference;
import sg.ic.umx.model.Application;
import sg.ic.umx.model.Server;
import sg.ic.umx.util.json.JsonHelper;

public class ApplicationRowMapper implements RowMapper<Application> {

    public ApplicationRowMapper() {
        // Empty Constructor
    }

    @Override
    public Application map(ResultSet rs, StatementContext ctx) throws SQLException {

        Application application = new Application();
        application.setId(rs.getLong("id"));
        application.setName(rs.getString("name"));
        application.setConfigurationName(rs.getString("configurationName"));
        application.setAttributeList(fromJson(rs.getString("attributeList")));
        application.setRecipientList(fromJson(rs.getString("recipientList")));
        application.setMailBody(rs.getString("mailBody"));
        application.setMailSubject(rs.getString("mailSubject"));
        application.setStatus(rs.getBoolean("status"));

        Server server = new Server(rs.getString("serverId"));
        application.setServer(server);

        return application;
    }

    private List<String> fromJson(String json) {
        return JsonHelper.fromJson(json, new TypeReference<List<String>>() {});
    }

}
