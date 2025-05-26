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
        String sql = """
        SELECT DISTINCT project.*
        FROM project
        LEFT JOIN projectassignees ON project.projectID = projectassignees.projectID
        WHERE project.employeeID = ? OR projectassignees.employeeID = ?
        """;
        return jdbcTemplate.query(sql, new ProjectRowmapper(), employeeID, employeeID);
    }

    public Project getProjectById(int projectId) {
        try {
            String sql = "SELECT * FROM project WHERE projectID = ?";
            Project project = jdbcTemplate.queryForObject(sql, new ProjectRowmapper(), projectId);
            project.setSubtasks(getAllSubtasksByProjectId(projectId));
            return project;
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

        String sql = "INSERT INTO subproject (subprojectID, subproject_name, subproject_start_date, subproject_end_date, projectID, subproject_description) VALUES(?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setInt(1, subProject.getSubprojectID());
                ps.setString(2, subProject.getSubprojectName());
                ps.setDate(3, subProject.getSubprojectStartDate());
                ps.setDate(4, subProject.getSubprojectEndDate());
                ps.setInt(5, subProject.getProjectID());
                ps.setString(6, subProject.getSubprojectDescription());
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
        for (SubProject subproject : subProjects) {
            subproject.setSubtasks(getAllSubtasksBySubprojectID(subproject.getSubprojectID()));
        }
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


        SubProject subproject = jdbcTemplate.query(sql, new SubprojectRowMapper(), id).getFirst();
        subproject.setSubtasks(getAllSubtasksBySubprojectID(subproject.getSubprojectID()));
        return subproject;
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

    //henter alle employees som ikke er projektleder og admin, så dem der har role 1
    public List<Employee> getAllCommonWorkers(){
        String sql = "SELECT * FROM employee WHERE roleID =?";
        return jdbcTemplate.query(sql, new EmployeeRowmapper(), 1);
    }

    public List<SubTask> getAllSubtasksByProjectId(int projectid) {
        String sql = """
                SELECT subtask.* FROM subtask
                JOIN task ON subtask.taskID = task.taskID
                JOIN subproject ON task.subProjectId = subproject.subprojectID
                JOIN project ON subproject.projectID = project.projectID
                WHERE project.projectID = ?;
                """;
        return jdbcTemplate.query(sql, new SubTaskRowMapper(), projectid);
    }

    public List<SubTask> getAllSubtasksBySubprojectID(int subprojectID) {
        String sql = """
                SELECT subtask.* FROM subtask
                JOIN task ON subtask.taskID = task.taskID
                JOIN subproject ON task.subProjectId = subproject.subprojectID
                WHERE subproject.subprojectID = ?;
                """;
        return jdbcTemplate.query(sql, new SubTaskRowMapper(), subprojectID);

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

    public boolean isUsernameFree(String employee, int id){
        String sql = "SELECT * FROM employee WHERE employee_username = ?";
        List<Employee>employeeList = jdbcTemplate.query(sql, new EmployeeRowmapper(), employee);
        if(employeeList.isEmpty()){
            return true;
        } //vi får en liste af employees som hedder det navn vi prøver at opdatere vores employee til
        //vores egen employee object kommer til at indgå i listen, hvis vi ikke har opdateret brugernavn
        //men vi måske kun har opdateret vores email eller lignende
        //derfor tjekker vi om employeeId matcher
        for(Employee i: employeeList){
            if(i.getEmployeeId() != id){
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
        String sql = "UPDATE task SET task_Name = ?, task_start_date = ?, task_end_date = ?, task_priority = ?, task_description = ? WHERE taskID = ?";
        jdbcTemplate.update(sql, task.getTaskName(), task.getTaskStartDate(), task.getTaskEndDate(), task.getTaskPriority(), task.getTaskDescription(), task.getTaskId());

    }

    public void createTask (Task task) {
        String sql = "INSERT INTO task (task_Name, subProjectId, task_start_date, task_end_date, task_priority, task_description) VALUES(?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();



        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, task.getTaskName());
                ps.setInt(2, task.getSubprojectId());
                ps.setDate(3, task.getTaskStartDate());
                ps.setDate(4, task.getTaskEndDate());
                ps.setString(5, task.getTaskPriority());
                ps.setString(6, task.getTaskDescription());
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
        String sql = "UPDATE subtask SET subtask_Name = ?, subtask_estimate = ?, subtask_start_date = ?, subtask_end_date = ?, subtask_priority = ?, subtask_description = ?, subtask_status = ?, subtask_hours_spent = ? WHERE subtaskID = ?";
        jdbcTemplate.update(sql, subtask.getSubtaskName(), subtask.getSubtaskEstimate(), subtask.getSubtaskStartDate(), subtask.getSubtaskEndDate(), subtask.getSubtaskPriority(), subtask.getSubtaskDescription(), subtask.getSubtaskStatus(), subtask.getSubtaskHoursSpent(), subtask.getSubtaskID());
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

    public List<Employee>getProjectOwner(int employeeId){
        String sql =
        "SELECT employee.* from employee " +
                "join project on project.employeeID = employee.employeeId " +
                "where project.projectID = ?";
        List<Employee>employeeList = jdbcTemplate.query(sql, new EmployeeRowmapper(), employeeId);
        if(employeeList.isEmpty()){
            return null;
        }

        return employeeList;
    }


    public List<Employee> getAllEmployeesWithSkillNotPartOfProject(String skill, int projectId) {
        List<Employee> finalList = new ArrayList<>();
        Employee projectOwner = getProjectOwner(projectId).getFirst(); //vi får ejeren af projektet fra tabelen project

        String sqlStart = "SELECT e.* FROM employee e "; //vi vil gerne ende med en liste af employees

        String sqlJoinSkill = "JOIN skillrelation sr ON e.employeeId = sr.employeeid " +
                "JOIN skill s ON sr.skillid = s.skillid "; //vi laver joins mellem tabellerne employee, skillrelation og skill

        String sqlNotInProject = "WHERE e.employeeid NOT IN (" +
                "  SELECT pa.employeeid FROM projectassignees pa WHERE pa.projectid = ?" + //her indsætter vi projectId
                ") "; //Vi vil kun have de employees som ikke allerde er en del af projektet.
        String sqlWithoutOwner = "AND e.employeeid != ? "; //her indsætter vi employeeId, så vi ikke får ejeren af projektet
        String sqlFilterSkill = "AND s.skill_name = ? "; //her indsætter vi den givne skill, som vi sortere efter

        String finalSql; //starter med at lave en tom string, som efterfølgende bliver udfyldt med vores sql query
        Object[] insertIntoQuery; //lav et tom array af datatypen Object

        if (skill == null || skill.isEmpty()) { //tjekker at skill er null eller bare en tom string
            finalSql = sqlStart + sqlNotInProject + sqlWithoutOwner;
            // sammensætter vores string queries, nu får vi en liste over alle dem der ikke er i projektet, vi har ikke sorteret efter skills
            insertIntoQuery = new Object[] { projectId, projectOwner.getEmployeeId() };
            //vi smider nu projectId og ejer af projektets id i en array
        } else { //i nedenstående metode, har vi bare sorteret dem ud for skills, så vi fx får alle dem der kan java
            finalSql = sqlStart + sqlJoinSkill + sqlNotInProject + sqlWithoutOwner + sqlFilterSkill;
            insertIntoQuery = new Object[] { projectId, projectOwner.getEmployeeId(), skill };
        } //nu kan vi så lave en liste af employees, enten kun dem med en given skill eller bare alle der ikke er en del af projektet
        List<Employee> employeeList = jdbcTemplate.query(finalSql, new EmployeeRowmapper(), insertIntoQuery);
        //til sidst iterere vi igennem vores liste og for hver person sætter vi deres skills på dem.
        for (Employee e : employeeList) {
            List<Skill> skills = getSkillsForEmployee(e.getEmployeeId());
            e.setSkills(skills);
            finalList.add(e);
        }
        return finalList;
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
        List<Project> projects = jdbcTemplate.query(sql, new ProjectRowmapper(), employeeId);

        for (Project project : projects) {
            project.setSubtasks(getAllSubtasksByProjectId(project.getProjectId()));
        }
        return projects;
    }


    public void updateSubproject(SubProject subProject) {
        String sql = "UPDATE subproject SET subproject_Name = ?, subproject_start_date = ?, subproject_end_date = ?, subproject_description = ? WHERE subprojectID = ?";
        int rowsAffected = jdbcTemplate.update(
                sql,
                subProject.getSubprojectName(),
                subProject.getSubprojectStartDate(),
                subProject.getSubprojectEndDate(),
                subProject.getSubprojectDescription(),
                subProject.getSubprojectID()
        );

        if (rowsAffected == 0) {
            throw new RuntimeException("Update failed: No subproject found with ID " + subProject.getSubprojectID());
        }
    }

    public Employee findByUsername(String username) {
        try {
            String sql = "SELECT * FROM employee WHERE employee_username = ?";
            Employee emp = jdbcTemplate.queryForObject(sql, new EmployeeRowmapper(), username);
            return emp;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public AwaitingEmployee createUser(AwaitingEmployee employee){
        try {
            String sql = "INSERT INTO awaitingemployee (awaitingEmployee_name, awaitingEmployee_email, awaitingEmployee_username, awaitingEmployee_password, awaitingEmployee_status) VALUES (?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, employee.getAwaitingEmployee_name());
                ps.setString(2, employee.getAwaitingEmployee_email());
                ps.setString(3, employee.getAwaitingEmployee_username());
                ps.setString(4, employee.getAwaitingEmployee_password());
                ps.setString(5, "Awaiting admin approval");
                return ps;
            }, keyHolder);

            int employeeId = keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;
            if (employeeId != -1) {
                employee.setAwaitingEmployeeID(employeeId);
            }

        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to register employee ", e);
        }

        return employee;
    }

    public List<AwaitingEmployee> getAllAwaitingUsers() {
        String sql = "SELECT * FROM awaitingemployee";
        return jdbcTemplate.query(sql, new AwaitingEmployeeRowMapper());
    }

    public void deleteAwaitingEmployee(int id) {
        String sql = "DELETE FROM awaitingemployee where awaitingEmployeeID = ?;";
        jdbcTemplate.update(sql,id);
    }

    public void deleteAwaitingEmployeeWithUsername(String username) {
        String sql = "DELETE FROM awaitingemployee where awaitingEmployee_username = ?;";
        jdbcTemplate.update(sql, username);
    }

    public void addAssigneeToTask(int taskId, List<Integer> employeeIds) {
        for (Integer emp : employeeIds) {
            String sql = "INSERT INTO taskassignees (taskID, employeeID) VALUES (?, ?)";
            jdbcTemplate.update(sql,taskId,emp);
        }
    }

    public int getProjectIdFromSubprojectId(int subprojectId) {
        String sql = "SELECT projectID FROM subproject WHERE subprojectID = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, subprojectId);
    }

    public List<Integer> getTaskAssignees(int taskId) {
        String sql = """
        SELECT *
        FROM employee
        JOIN taskassignees ON employee.employeeID = taskassignees.employeeID
        WHERE taskassignees.taskID = ?
    """;
        List<Employee> employees = jdbcTemplate.query(sql,new EmployeeRowmapper(),taskId);
        List<Integer> empIds = new ArrayList<>();

        for (Employee emp : employees) {
            empIds.add(emp.getEmployeeId());
        }

        return empIds;
    }

    public void clearTaskAssignees(int taskId) {
        String sql = "DELETE FROM taskassignees WHERE taskID = ?";
        jdbcTemplate.update(sql, taskId);
    }

    public void addAssigneeToSubtask(int subtaskId, List<Integer> employeeIds) {
        for (Integer emp : employeeIds) {
            String sql = "INSERT INTO subtaskassignees (subtaskID, employeeID) VALUES (?, ?)";
            jdbcTemplate.update(sql,subtaskId,emp);
        }
    }

    public List<Integer> getSubtaskAssignees(int subtaskId) {
        String sql = """
        SELECT *
        FROM employee
        JOIN subtaskassignees ON employee.employeeID = subtaskassignees.employeeID
        WHERE subtaskassignees.subtaskID = ?
    """;
        List<Employee> employees = jdbcTemplate.query(sql,new EmployeeRowmapper(),subtaskId);
        List<Integer> empIds = new ArrayList<>();

        for (Employee emp : employees) {
            empIds.add(emp.getEmployeeId());
        }

        return empIds;
    }

    public void clearSubtaskAssignees(int subtaskId) {
        String sql = "DELETE FROM subtaskassignees WHERE subtaskID = ?";
        jdbcTemplate.update(sql, subtaskId);
    }


    public boolean isUsernameAwaitingUserFree(String employee, int id) {
        String sql = "SELECT * FROM awaitingemployee WHERE awaitingEmployee_name = ?";
        List<AwaitingEmployee> employeeList = jdbcTemplate.query(sql, new AwaitingEmployeeRowMapper(), employee);
        if (employeeList.isEmpty()) {
            return true;
        }
        for (AwaitingEmployee i : employeeList) {
            if (i.getAwaitingEmployeeID() != id) {
                return false;
            }
        }
        return true;
    }

    public List<Project> getProjectsWithAssignees(int empId) {
        List<Project> projects = getAllProjects(empId);
        for(Project p : projects) {
            p.setAssignees(getProjectAssignees(p.getProjectId()));
            p.setSubtasks(getAllSubtasksByProjectId(p.getProjectId()));
        }

        return projects;
    }


    public List<SubTask> getSortedSubtaskByEmployeeIdPerfected(String sortColumn, int employeeId) {
        List<String> allowedSortColumns = List.of("subtask_estimate", "subtask_end_date", "subtask_end_date desc", "subtask_priority");
        String order =""; //inizialiter en tom string
        if (sortColumn != null && !sortColumn.isBlank()) { // checker om vores sortering input er en tom string
            if (allowedSortColumns.contains(sortColumn)) { //checker om vores sortering input er en af dem i vores DB
                order = "ORDER BY "; //starter vores string med mysql sorterings formen "order by"
                order += sortColumn; //definere hvad vi vil sortere efter fx, slut dato
            }
        } //laver en query, hvor vi vil have alle subtask for en given employee og evt sortere dem ud for en parameter
        String sql = "SELECT subtask.* FROM subtask " +
                "JOIN subtaskassignees ON subtask.subtaskId = subtaskassignees.subtaskID " +
                "WHERE subtaskassignees.employeeId = ? " + order;
        List<SubTask> subTaskList = jdbcTemplate.query(sql, new SubTaskRowMapper(), employeeId);
        //vi får forhåbenligt en liste med subtask tilbage, hvis den er tom, så returnere vi null
        if(subTaskList.isEmpty()){
            return null;
        }//sætter subprojectId på subtask objektet
        for(SubTask b: subTaskList){
            b.setSubProjectId(getSubProjectIdWithSubTaskId(b.getSubtaskID()));
        }//nedenstående metode er nødvendig, fordi "order by" kommer til at sortere alfabetisk ved priority
        //så sortere den efter high, low og så medium, fordi "l" kom før "m" i alfabetet, metoden sortere dem så efter den rigtige prioritet
        if(sortColumn != null && sortColumn.equalsIgnoreCase("subtask_priority")){
            return sortSubtasksByPriority(subTaskList);
        }
        return subTaskList;
    }

    public int getSubProjectIdWithSubTaskId(int subtaskId){
        String sql ="SELECT task.* from task " +
                "join subtask on task.taskId = subtask.taskId " +
                "where subtask.subtaskId = ?";
        List<Task>subProjects = jdbcTemplate.query(sql, new TaskRowMapper(), subtaskId);
        if(subProjects.isEmpty()){
            return 0;
        }
        return subProjects.getFirst().getSubprojectId();
    }
    public void deleteSkill(int skillId){
        String sql ="DELETE FROM skill WHERE skillID = ?";
        jdbcTemplate.update(sql, skillId);
    }



    public void removeAssigneeFromProject(int projectId, int employeeId) {
        String sql = "DELETE FROM projectAssignees WHERE projectID = ? AND employeeID = ?";
        jdbcTemplate.update(sql, projectId, employeeId);

    }

    public Skill createSkill(Skill skill){
        try{
        String sql ="INSERT INTO skill (skill_name) VALUES (?) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, skill.getSkillName());
            return ps;
        }, keyHolder);

        int skillId = keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;
        if(skillId != -1){
            skill.setSkillId(skillId);
        }
        } catch (DataAccessException e) {
        throw new RuntimeException("Failed to register skill ", e);
         }
        return skill;
    }
    public boolean skillNotInDb(Skill skill){
        String sqlChecker = "SELECT * FROM skill WHERE skill_name = ?";
        List<Skill> checkIfSkillExists = jdbcTemplate.query(sqlChecker, new SkillRowmapper(), skill.getSkillName());
        if(!checkIfSkillExists.isEmpty()){ //checker om den givne skill allerede er i DB
            return false;
        }
        return true;
    }
    public List<SubTask>sortSubtasksByPriority(List<SubTask>subTasks){
        List<SubTask> subTasksList = new ArrayList<>();
        if(subTasks == null || subTasks.isEmpty()){
            return null;
        }
        for(SubTask i: subTasks){
            if(i.getSubtaskPriority().equalsIgnoreCase("High")){
                subTasksList.add(i);
            }
        }
        for(SubTask b: subTasks){
            if(b.getSubtaskPriority().equalsIgnoreCase("Medium")){
                subTasksList.add(b);
            }
        }
        for(SubTask c: subTasks){
            if(c.getSubtaskPriority().equalsIgnoreCase("Low")){
                subTasksList.add(c);
            }
        }
        return subTasksList;
    }

}
