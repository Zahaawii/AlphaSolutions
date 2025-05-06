package apiassignment.alphasolutions.rowmapper;

import apiassignment.alphasolutions.model.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRowmapper implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Employee employee = new Employee();


        employee.setEmployeeId(rs.getInt("employeeID"));
        employee.setEmployeeName(rs.getString("employee_Name"));
        employee.setEmployeeEmail(rs.getString("employee_email"));
        employee.setEmployeeUsername(rs.getString("employee_username"));
        employee.setEmployeePassword(rs.getString("employee_password"));
        employee.setRoleId(rs.getInt("roleID"));

        return employee;
    }
}
