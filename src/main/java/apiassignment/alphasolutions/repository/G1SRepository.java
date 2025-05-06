package apiassignment.alphasolutions.repository;

import apiassignment.alphasolutions.model.*;
import apiassignment.alphasolutions.rowmapper.*;

import org.springframework.dao.DataAccessException;
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

    public List<SubProject> getAllSubProjects() {
        String sql = "SELECT * FROM subproject";
        return jdbcTemplate.query(sql, new SubprojectRowMapper());
    }

    public SubProject addSubProject(SubProject subProject) {

        String sql = "INSERT INTO subproject (subprojectID, subproject_name, subproject_start_date, subproject_end_date, projectID) VALUES(?, ?, ?, ?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setInt(1, subProject.getSubprojectID());
                ps.setString(2, subProject.getSubprojectName());
                ps.setDate(3, subProject.getSubprojectStartDate());
                ps.setDate(4, subProject.getSubprojectEndDate());
                ps.setInt(5, 1);
                return ps;
            }, keyHolder);

            int subprojectID = keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;

            if (subprojectID != -1) {
                subProject.setSubprojectID(subprojectID);
            }
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to create the subproject in the database: ", e);
        }


        return subProject;
    }

    public List<SubProject> getSubprojectByProjectId(int id) {
        String sql = "SELECT * FROM subproject WHERE projectID = ?";
        List<SubProject> subProjects = jdbcTemplate.query(sql, new SubprojectRowMapper(), id);

        return  subProjects;
    }

    public List<Task> getTasksBySubprojectId(int id) {
        String sql = "SELECT * FROM task WHERE subProjectId = ?";
        List<Task> tasks = jdbcTemplate.query(sql, new TaskRowMapper(), id);

        for (Task task : tasks) {
            task.setSubtasks(getSubtasksByTaskId(task.getTaskId()));
        }

        return tasks;
    }

    public SubProject getSubProjectById (int id) {
        String sql = "SELECT * FROM subproject WHERE subprojectID = ?";
        return jdbcTemplate.query(sql, new SubprojectRowMapper(), id).getFirst();
    }

    public List<SubTask> getSubtasksByTaskId(int id) {
        String sql = "SELECT * FROM subtask WHERE taskID = ?";
        return jdbcTemplate.query(sql, new SubTaskRowMapper(), id);
    }

    public List<Employee> getEmployeesByTaskId (int id) {
        String sql = """
        SELECT *
        FROM employee
        JOIN taskassignees ON employee.employeeID = taskassignees.employeeID
        WHERE taskassignees.taskID = ?
    """;

        return jdbcTemplate.query(sql, new EmployeeRowmapper(), id);
    }

    public List<Employee> getEmployeesBySubtaskId(int id) {
        String sql = """
        SELECT *
        FROM employee
        JOIN subtaskassignees ON employee.employeeID = subtaskassignees.employeeID
        WHERE subtaskassignees.subtaskID = ?
    """;
        return jdbcTemplate.query(sql, new EmployeeRowmapper(), id);
    }

    //henter alle employees i systemet
    public List<Employee>getAllEmployee(){
        String sql = "SELECT * FROM employee";
        return jdbcTemplate.query(sql, new EmployeeRowmapper());
    }

    //henter alle employees som ikke er projektleder og admin, så dem der har role 1
    public List<Employee> getAllCommonWorkers(){
        String sql = "SELECT * FROM employee WHERE roleID =?";
        return jdbcTemplate.query(sql, new EmployeeRowmapper(), 1);
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

    public void deleteEmployee(int id){
        String sql ="DELETE FROM employee WHERE employeeID = ?";
        jdbcTemplate.update(sql, id);
    }

    public Employee getEmployeeById(int id){
        String sql = "SELECT * FROM employee WHERE employeeID = ?";
        List<Employee> employees = jdbcTemplate.query(sql, new EmployeeRowmapper(), id);
        if(employees.isEmpty()){
            return null;
        }
        return employees.getFirst();
    }

    public Employee updateEmployee(Employee employee){
        String sql = "UPDATE employee SET employee_name = ?, employee_email = ?, employee_username = ?, employee_password = ?, employee_password = ?, roleID = ? WHERE employeeID = ?";
        jdbcTemplate.update(sql, employee.getEmployeeName(), employee.getEmployeeEmail(), employee.getEmployeeUsername(), employee.getEmployeePassword(), employee.getRoleId(), employee.getRoleId());
        return employee;
    }

    public List<Role> getAllRoles(){
        String sql = "SELECT * from roles";
        return jdbcTemplate.query(sql, new RoleRowmapper());
    }

}
