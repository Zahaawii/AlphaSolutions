package apiassignment.alphasolutions.repository;

import apiassignment.alphasolutions.model.Employee;
import apiassignment.alphasolutions.model.Project;
import apiassignment.alphasolutions.model.Skill;
import apiassignment.alphasolutions.rowmappers.EmployeeRowmapper;
import apiassignment.alphasolutions.rowmappers.ProjectRowmapper;
import apiassignment.alphasolutions.rowmappers.SkillRowmapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        String sql = "SELECT * FROM project WHERE projectID = ?";
        return jdbcTemplate.queryForObject(sql, new ProjectRowmapper(), projectId);
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
        String deleteAssignees = "DELETE FROM projectassignees WHERE project.projectID = ?";
        String deleteProject = "DELETE FROM project WHERE project.projectID = ?";
        jdbcTemplate.update(deleteAssignees,projectID);
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

    public void assignEmployeesToProject(int projectId, List<Integer> employeeIds) {
        String sql = "INSERT INTO projectassignees (projectID, employeeID) VALUES (?, ?)";
        for (Integer empId : employeeIds) {
            jdbcTemplate.update(sql,projectId,employeeIds);
        }
    }

    public void clearProjectAssignees(int projectId) {
        String sql = "DELETE FROM projectassignees WHERE projectID = ?";
        jdbcTemplate.update(sql, projectId);
    }

    public List<Integer> getProjectAssignees(int projectId) {
        String sql = "SELECT employeeID FROM projectassignees WHERE projectID = ?";
        return jdbcTemplate.queryForList(sql, Integer.class, projectId);
    }

    public List<Employee> getAllEmployees() {
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
        List<Employee> all = getAllEmployees();
        List<Employee> selected = new ArrayList<>();

        for (Employee emp : all) {
            if (ids.contains(emp.getEmployeeID())) {
                selected.add(emp);
            }
        }

        return selected;
    }
}
