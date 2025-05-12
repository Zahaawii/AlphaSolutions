package apiassignment.alphasolutions.rowmapper;

import apiassignment.alphasolutions.model.SubTask;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubTaskRowMapper implements RowMapper<SubTask> {
    @Override
    public SubTask mapRow(ResultSet rs, int rowNum) throws SQLException {
        SubTask subtask = new SubTask();

        subtask.setSubtaskID(rs.getInt("subtaskID"));
        subtask.setSubtaskName(rs.getString("subtask_Name"));
        subtask.setTaskID(rs.getInt("taskID"));
        subtask.setSubtaskEstimate(rs.getInt("subtask_estimate"));
        subtask.setSubtaskStartDate(rs.getDate("subtask_start_date"));
        subtask.setSubtaskEndDate(rs.getDate("subtask_end_date"));
        subtask.setSubtaskPriority(rs.getString("subtask_priority"));
        subtask.setSubtaskDescription(rs.getString("subtask_description"));
        subtask.setSubtaskStatus(rs.getString("subtask_status"));
        subtask.setSubtaskHoursSpent(rs.getInt("subtask_hours_spent"));

        return subtask;
    }
}
