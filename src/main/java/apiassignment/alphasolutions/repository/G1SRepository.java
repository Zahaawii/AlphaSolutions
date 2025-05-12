package apiassignment.alphasolutions.repository;


import apiassignment.alphasolutions.DTO.DTOEmployee;
import apiassignment.alphasolutions.model.*;
import apiassignment.alphasolutions.rowmapper.*;


import apiassignment.alphasolutions.rowmapper.ProjectRowmapper;
import apiassignment.alphasolutions.rowmapper.SkillRowmapper;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;


import java.sql.Statement;




@Repository
public class G1SRepository {
    private JdbcTemplate jdbcTemplate;

    public G1SRepository(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    // Returnerer alle projekterne en medarbejder er tilknyttet samt de projekter en projektleder har oprettet.
    public List<Project> getAllProjects(int employeeID) {
        String sql = "SELECT DISTINCT project.* \n" +
                "FROM project\n" +
                "LEFT JOIN projectassignees ON project.projectID = projectassignees.projectID\n" +
                "WHERE project.employeeID = ? OR projectassignees.employeeID = ?";
        return jdbcTemplate.query(sql, new ProjectRowmapper(), employeeID, employeeID);
    }

    public Project getProjectById(int projectId) {
        try {
            String sql = "SELECT * FROM project WHERE projectID = ?";
            return jdbcTemplate.queryForObject(sql, new ProjectRowmapper(), projectId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void createProject(Project project) {
        try {
            String sql = "INSERT INTO project (project_Name, project_status, project_start_date, project_end_date, employeeID, project_description) VALUES (?, ?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1,project.getProjectName());
                ps.setString(2,project.getProjectStatus());
                ps.setDate(3,project.getProjectStartDate());
                ps.setDate(4,project.getProjectEndDate());
                ps.setInt(5,project.getEmployeeId());
                ps.setString(6,project.getProjectDescription());
                return ps;
            }, keyHolder);

            int projectID = keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;

            if (projectID != -1) {
                project.setProjectId(projectID);
            }
            // Der anvendes en DataAccessException, da der håndteres en databaseoperation i metoden.
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to create project in database", e);
        }
    }

    public void deleteProject(int projectID) {
        String deleteProject = "DELETE FROM project WHERE projectID = ?";
        jdbcTemplate.update(deleteProject,projectID);
    }

    public void updateProject(Project project) {
        String sql = "UPDATE project SET project_Name = ?, project_status = ?, project_start_date = ?, project_end_date = ?, project_description = ? WHERE project.projectID = ?";
        jdbcTemplate.update(sql,project.getProjectName(),
                project.getProjectStatus(),
                project.getProjectStartDate(),
                project.getProjectEndDate(),
                project.getProjectDescription(),
                project.getProjectId()
        );
    }


    public void clearProjectAssignees(int projectId) {
        String sql = "DELETE FROM projectassignees WHERE projectID = ?";
        jdbcTemplate.update(sql, projectId);
    }

    public List<Employee> getProjectAssignees(int projectId) {
        String sql = """
        SELECT *
        FROM employee
        JOIN projectassignees ON employee.employeeID = projectassignees.employeeID
        WHERE projectassignees.projectID = ?
    """;
        return jdbcTemplate.query(sql, new EmployeeRowmapper(), projectId);
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
                ps.setInt(5, subProject.getProjectID());
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

    public void deleteSubProject(int id) {
        String sql = "DELETE FROM subproject WHERE subprojectID = ?";
        jdbcTemplate.update(sql, id);
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
    public List<Employee> getAllEmployee(){

        String sql = "SELECT * FROM employee";
        return jdbcTemplate.query(sql, new EmployeeRowmapper());
    }


    public List<Skill> getSkillsByEmployeeId(int employeeId) {
        String sql = "SELECT skill.skillID, skill.skill_name\n" +
                "FROM skill\n" +
                "JOIN skillRelation ON skill.skillID = skillRelation.skillID\n" +
                "WHERE skillRelation.employeeID = ?";
        return jdbcTemplate.query(sql, new SkillRowmapper(), employeeId);
    }

    public List<Employee> getEmployeesByIds(List<Integer> ids) {
        List<Employee> all = getAllEmployee();
        List<Employee> selected = new ArrayList<>();

        for (Employee emp : all) {
            if (ids.contains(emp.getEmployeeId())) {
                selected.add(emp);
            }
        }

        return selected;
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

    public boolean isUsernameFree(DTOEmployee employee){
        String sql = "SELECT * FROM employee WHERE employee_username = ?";
        List<Employee>employeeList = jdbcTemplate.query(sql, new EmployeeRowmapper(), employee.getEmployeeUsername());
        if(employeeList.isEmpty()){
            return true;
        } //vi får en liste af employees som hedder det navn vi prøver at opdatere vores employee til
        //vores egen employee object kommer til at indgå i listen, hvis vi ikke har opdateret brugernavn
        //men vi måske kun har opdateret vores email eller lignende
        //derfor tjekker vi om employeeId matcher
        for(Employee i: employeeList){
            if(i.getEmployeeId() != employee.getEmployeeId()){
                return false;
            }
        }
        return true;
    }


    public DTOEmployee adminRegisterEmployee(DTOEmployee employee){
        try {
            String sql = "INSERT INTO employee (employee_name, employee_email, employee_username, employee_password, roleID) VALUES (?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, employee.getEmployeeName());
                ps.setString(2, employee.getEmployeeEmail());
                ps.setString(3, employee.getEmployeeUsername());
                ps.setString(4, employee.getEmployeePassword());
                ps.setInt(5, employee.getRoleId());
                return ps;
            }, keyHolder);

            int employeeId = keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;
            if (employeeId != -1) {
                employee.setEmployeeId(employeeId);
            }
            if(employee.getSkills() != null && !employee.getSkills().isEmpty()) {
                for(Integer i : employee.getSkills()){
                    String sqlInsertSkill = "INSERT INTO skillrelation (skillID, employeeID) VALUES (?, ?)";
                    jdbcTemplate.update(sqlInsertSkill, i, employee.getEmployeeId());
                }
            }
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to register employee ", e);
        }

        return employee;
    }

    public void deleteEmployee(int id){
        String sql ="DELETE FROM employee WHERE employeeID = ?";
        jdbcTemplate.update(sql, id);
    }

    public Task getTaskById(int id) {
        String sql = "SELECT * FROM task WHERE taskID = ?";
        Task task = jdbcTemplate.query(sql, new TaskRowMapper(), id).getFirst();

        //populate task with assignees
        task.setAssignees(getEmployeesByTaskId(id));
        return task;
    }

    public void deleteTask(int id) {
        String sql = "DELETE FROM task WHERE taskID = ?";
        jdbcTemplate.update(sql, id);
    }

    public void updateTask(Task task) {
        String sql = "UPDATE task SET task_Name = ?, task_estimate = ?, task_start_date = ?, task_end_date = ?, task_priority = ?, task_description = ?, task_status = ? WHERE taskID = ?";
        jdbcTemplate.update(sql, task.getTaskName(), task.getTaskEstimate(), task.getTaskStartDate(), task.getTaskEndDate(), task.getTaskPriority(), task.getTaskDescription(), task.getTaskStatus(), task.getTaskId());

    }

    public void createTask (Task task) {
        String sql = "INSERT INTO task (task_Name, subProjectId, task_estimate, task_start_date, task_end_date, task_priority, task_description, task_status) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();



        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, task.getTaskName());
                ps.setInt(2, task.getSubprojectId());
                ps.setInt(3, task.getTaskEstimate());
                ps.setDate(4, task.getTaskStartDate());
                ps.setDate(5, task.getTaskEndDate());
                ps.setString(6, task.getTaskPriority());
                ps.setString(7, task.getTaskDescription());
                ps.setString(8, task.getTaskStatus());
                return ps;
            }, keyHolder);

            int taskID = keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;

            if (taskID != -1) {
                task.setTaskId(taskID);
            }


        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to create the task in the database: ", e);
        }

    }

    public void updateSubtask(SubTask subtask) {
        String sql = "UPDATE subtask SET subtask_Name = ?, subtask_estimate = ?, subtask_start_date = ?, subtask_end_date = ?, subtask_priority = ?, subtask_description = ?, subtask_status = ? WHERE subtaskID = ?";
        jdbcTemplate.update(sql, subtask.getSubtaskName(), subtask.getSubtaskEstimate(), subtask.getSubtaskStartDate(), subtask.getSubtaskEndDate(), subtask.getSubtaskPriority(), subtask.getSubtaskDescription(), subtask.getSubtaskStatus(), subtask.getSubtaskID());
    }

    public void deleteSubtask(int id) {
        String sql = "DELETE FROM subtask WHERE subtaskID = ?";
        jdbcTemplate.update(sql, id);
    }

    public void createSubTask (SubTask subtask) {
        String sql = "INSERT INTO subtask (subtask_Name, taskID, subtask_estimate, subtask_start_date, subtask_end_date, subtask_priority, subtask_description, subtask_status) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();


        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, subtask.getSubtaskName());
                ps.setInt(2, subtask.getTaskID());
                ps.setInt(3, subtask.getSubtaskEstimate());
                ps.setDate(4, subtask.getSubtaskStartDate());
                ps.setDate(5, subtask.getSubtaskEndDate());
                ps.setString(6, subtask.getSubtaskPriority());
                ps.setString(7, subtask.getSubtaskDescription());
                ps.setString(8, subtask.getSubtaskStatus());
                return ps;
            }, keyHolder);

            int subtaskID = keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;

            if (subtaskID != -1) {
                subtask.setSubtaskID(subtaskID);
            }


        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to create the subtask in the database: ", e);
        }

    }

    public Employee getEmployeeById(int id){
        String sql = "SELECT * FROM employee WHERE employeeID = ?";
        List<Employee> employees = jdbcTemplate.query(sql, new EmployeeRowmapper(), id);
        if(employees.isEmpty()){
            return null;
        }
        return employees.getFirst();
    }

    public SubTask getSubtaskById(int id) {
        String sql = "SELECT * FROM subtask WHERE subtaskID = ?";
        return jdbcTemplate.query(sql, new SubTaskRowMapper(), id).getFirst();
    }

    public DTOEmployee updateEmployee(DTOEmployee employee){
        String sql = "UPDATE employee SET employee_name = ?, employee_email = ?, employee_username = ?, employee_password = ?, roleID = ? WHERE employeeID = ?";
        jdbcTemplate.update(sql, employee.getEmployeeName(), employee.getEmployeeEmail(), employee.getEmployeeUsername(), employee.getEmployeePassword(), employee.getRoleId(), employee.getEmployeeId());
        String sqlDeleteSkill = "DELETE FROM skillrelation WHERE employeeID = ?";
        jdbcTemplate.update(sqlDeleteSkill, employee.getEmployeeId());
        if(employee.getSkills() != null && !employee.getSkills().isEmpty()) {
            for(Integer i : employee.getSkills()){
                String sqlInsertSkill = "INSERT INTO skillrelation (skillID, employeeID) VALUES (?, ?)";
                jdbcTemplate.update(sqlInsertSkill, i, employee.getEmployeeId());
            }
        }
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
                "join projectassignees on projectassignees.projectID = project.projectID " +
                "join employee on employee.employeeID = projectassignees.employeeID " +
                "where employee.employeeID = ?";
        return jdbcTemplate.query(sql, new ProjectRowmapper(), employeeId);
    }


    public void updateSubproject(SubProject subProject) {
        String sql = "UPDATE subproject SET subproject_Name = ?, subproject_start_date = ?, subproject_end_date = ? WHERE subprojectID = ?";
        int rowsAffected = jdbcTemplate.update(
                sql,
                subProject.getSubprojectName(),
                subProject.getSubprojectStartDate(),
                subProject.getSubprojectEndDate(),
                subProject.getSubprojectID()
        );

        if (rowsAffected == 0) {
            throw new RuntimeException("Update failed: No subproject found with ID " + subProject.getSubprojectID());
        }
    }



}
