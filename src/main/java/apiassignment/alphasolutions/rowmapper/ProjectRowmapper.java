package apiassignment.alphasolutions.rowmapper;

import apiassignment.alphasolutions.model.Project;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectRowmapper implements RowMapper<Project> {
    @Override
    public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
        Project project = new Project();

        project.setProjectId(rs.getInt("projectID"));
        project.setProjectName(rs.getString("project_Name"));
        project.setProjectStatus(rs.getString("project_status"));
        project.setProjectStartDate(rs.getDate("project_start_date"));
        project.setProjectEndDate(rs.getDate("project_end_date"));
        project.setProjectDescription(rs.getString("project_description"));
        project.setEmployeeId(rs.getInt("employeeID"));

        return project;
    }
}