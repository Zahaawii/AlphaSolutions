package apiassignment.alphasolutions.rowmapper;

import apiassignment.alphasolutions.model.AwaitingEmployee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class AwaitingEmployeeRowMapper implements RowMapper<AwaitingEmployee> {

    @Override
    public AwaitingEmployee mapRow(ResultSet rs, int rowNum) throws SQLException {
        AwaitingEmployee Awaitingemployee = new AwaitingEmployee();


        Awaitingemployee.setAwaitingEmployeeID(rs.getInt("awaitingEmployeeID"));
        Awaitingemployee.setAwaitingEmployee_name(rs.getString("awaitingEmployee_name"));
        Awaitingemployee.setAwaitingEmployee_email(rs.getString("awaitingEmployee_email"));
        Awaitingemployee.setAwaitingEmployee_username(rs.getString("awaitingEmployee_username"));
        Awaitingemployee.setAwaitingEmployee_password(rs.getString("awaitingEmployee_password"));
        Awaitingemployee.setAwaitingEmployee_status(rs.getString("awaitingEmployee_status"));

        return Awaitingemployee;
    }
}
