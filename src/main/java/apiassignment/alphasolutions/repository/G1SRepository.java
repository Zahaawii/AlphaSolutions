package apiassignment.alphasolutions.repository;

import apiassignment.alphasolutions.model.SubProject;
import apiassignment.alphasolutions.rowmapper.SubprojectRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class G1SRepository {

    private JdbcTemplate jdbcTemplate;

    public G1SRepository(JdbcTemplate jdbcTemplate){
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

}
