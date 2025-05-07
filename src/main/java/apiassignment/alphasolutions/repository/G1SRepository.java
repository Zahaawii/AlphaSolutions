package apiassignment.alphasolutions.repository;


import apiassignment.alphasolutions.model.*;
import apiassignment.alphasolutions.rowmapper.*;

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


    public List<Task> getTasksBySubprojectId(int id) {
        String sql = "SELECT * FROM task WHERE subProjectId = ?";
        List<Task> tasks = jdbcTemplate.query(sql, new TaskRowMapper(), id);

        //populate list of subtasks inside each task
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

    public void deleteTask(int id) {
        String sql = "DELETE FROM task WHERE taskID = ?";
        jdbcTemplate.update(sql, id);
    }

    public void deleteSubtask(int id) {
        String sql = "DELETE FROM subtask WHERE subtaskID = ?";
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
    public List<Skill>getAllSkills(){
        String sql = "SELECT * FROM skill";
        return jdbcTemplate.query(sql, new SkillRowmapper());
    }

    public List<Employee>getEmployeeBySkills(String skills){
        String sql = "SELECT employee.* from employee " +
                "join skillrelation on employee.employeeID = skillrelation.employeeID " +
                "join skill on skill.skillID = skillrelation.skillID " +
                "where skill_name = ?";
        List<Employee> listOfEmployees = jdbcTemplate.query(sql, new EmployeeRowmapper(), skills);
        if(listOfEmployees.isEmpty()){
            return null;
        }
        return listOfEmployees;
    }
    public List<Employee>getEmployeeNotPartOfProject(int projectId){
        String sql ="SELECT * FROM employee " +
                "WHERE employeeID NOT IN (" +
                "    SELECT employeeID " +
                "    FROM projectassignees " +
                "    WHERE projectID = ? )";
        return jdbcTemplate.query(sql, new EmployeeRowmapper(), projectId);
    }

    public void addEmployeeToProject(int projectId, int employeeId){
        String sql = "INSERT INTO projectassignees (projectId, employeeId) VALUES (?, ?)";
        jdbcTemplate.update(sql, projectId, employeeId);
    }
    public List<Skill>getSkillsForEmployee(int employeeId){
        String sql = "SELECT skill.* FROM skill " +
                "JOIN skillrelation ON skillrelation.skillID = skill.skillID " +
                "JOIN employee ON employee.employeeID = skillrelation.employeeID " +
                "WHERE employee.employeeID = ?";
        return jdbcTemplate.query(sql, new SkillRowmapper(), employeeId);
    }
    public List<Project>getProjectsForOneEmployee(int employeeId){
        String sql = "SELECT project.* from project " +
                "join projectassginees on projectassginees.projectID = project.projectID " +
                "join employee on employee.employeeID = projectassginees.employeeID " +
                "where employee.employeeID = ?";
        return jdbcTemplate.query(sql, new ProjectRowmapper(), employeeId);
    }




}
