package apiassignment.alphasolutions.rowmapper;


import apiassignment.alphasolutions.model.SubProject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubprojectRowMapper implements RowMapper<SubProject> {

    @Override
    public SubProject mapRow(ResultSet rs, int rowNum) throws SQLException {

        SubProject subProject = new SubProject();

        subProject.setSubprojectID(rs.getInt("subprojectID"));
        subProject.setSubprojectName(rs.getString("subproject_Name"));
        subProject.setSubprojectStartDate(rs.getDate("subproject_start_date"));
        subProject.subprojectEndDate(rs.getDate("subproject_end_date"));
        subProject.setSubprojectEndDate(rs.getDate("subproject_end_date"));
        subProject.setProjectID(rs.getInt("projectID"));

        return subProject;

    }
}
