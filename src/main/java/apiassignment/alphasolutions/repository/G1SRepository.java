package apiassignment.alphasolutions.repository;

import apiassignment.alphasolutions.model.Project;
import apiassignment.alphasolutions.rowmappers.ProjectRowmapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class G1SRepository {
    private JdbcTemplate jdbcTemplate;

    public G1SRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Project> getAllProjects(int employeeID) {
        String sql = "SELECT project.* FROM project WHERE project.employeeID = ?";
        return jdbcTemplate.query(sql,new ProjectRowmapper(),employeeID);
    }

    public void createProject(Project project) {
        try {
            String sql = "INSERT INTO project (project_Name, project_status, project_start_date, project_end_date, employeeID) VALUES (?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1,project.getProjectName());
                ps.setString(2,project.getProjectStatus());
                ps.setDate(3,project.getProjectStartDate());
                ps.setDate(4,project.getProjectEndDate());
                ps.setInt(5,project.getEmployeeId());
                return ps;
            }, keyHolder);

            int projectID = keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;

            if (projectID != -1) {
                project.setEmployeeId(projectID);
            }
            // Der anvendes en DataAccessException, da der håndteres en databaseoperation i metoden.
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to create project in database", e);
        }
    }

    public void deleteProject(int projectID) {
        String sql = "DELETE FROM project WHERE project.projectID = ?";
        jdbcTemplate.update(sql,projectID);
    }

    public void updateProject(Project project) {
        String sql = "UPDATE project SET project_Name = ?, project_status = ?, project_start_date = ?, project_end_date = ? WHERE project.projectID = ?";
        jdbcTemplate.update(sql,project.getProjectName(),project.getProjectStatus(),project.getProjectStartDate(),project.getProjectEndDate(),project.getProjectId());
    }

}
