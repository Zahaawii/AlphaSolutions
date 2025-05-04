package apiassignment.alphasolutions.rowmappers;

import apiassignment.alphasolutions.model.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRowmapper implements RowMapper<Employee> {
    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Employee emp = new Employee();
        emp.setEmployeeID(rs.getInt("employeeID"));
        emp.setEmployeeName(rs.getString("employee_Name"));
        emp.setEmployeeEmail(rs.getString("employee_email"));
        emp.setEmployeeUsername(rs.getString("employee_username"));
        emp.setRoleID(rs.getInt("roleID"));
        return emp;
    }
}

