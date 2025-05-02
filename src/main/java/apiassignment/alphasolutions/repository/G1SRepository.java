package apiassignment.alphasolutions.repository;

import apiassignment.alphasolutions.model.Employee;
import apiassignment.alphasolutions.rowmapper.EmployeeRowmapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class G1SRepository {
    private JdbcTemplate jdbcTemplate;

    public G1SRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Employee>getAllEmployee(){
        String sql = "SELECT * FROM employee";
        return jdbcTemplate.query(sql, new EmployeeRowmapper());
    }



    public Employee login(String username, String password) {
        String sql = "SELECT * FROM employee WHERE employee_username = ?";
        List<Employee> temp = jdbcTemplate.query(sql, new EmployeeRowmapper(), username);
        if (temp.isEmpty()) {
            return null;
        }
        Employee employee = temp.getFirst();
        if (!employee.getEmployeePassword().equals(password)) {
            return null;
        }
        return employee;
    }

    public boolean isUsernameFree(String username){
        String sql = "SELECT * FROM employee WHERE employee_username = ?";
        List<Employee>employees = jdbcTemplate.query(sql, new EmployeeRowmapper(), username);
        if(employees.isEmpty()){
            return true;
        }
        return false;
    }


    public Employee adminRegisterEmployee(Employee employee){
        if(employee == null){
            return null;
        }

        String sql = "INSERT INTO employee (employee_name, employee_email, employee_username, employee_password, roleID) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection->{
            PreparedStatement ps =connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, employee.getEmployeeName());
            ps.setString(2, employee.getEmployeeEmail());
            ps.setString(3, employee.getEmployeeUsername());
            ps.setString(4, employee.getEmployeePassword());
            ps.setInt(5, employee.getRoleId());
            return ps;
        }, keyHolder);

        int employeeId =keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;
        if(employeeId != -1){
            employee.setEmployeeId(employeeId);
        }

        return employee;
    }


}
