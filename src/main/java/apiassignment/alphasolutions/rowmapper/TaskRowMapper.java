package apiassignment.alphasolutions.rowmapper;

import apiassignment.alphasolutions.model.Task;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskRowMapper implements RowMapper<Task> {

    @Override
    public Task mapRow (ResultSet rs, int rowNum) throws SQLException {
        Task task = new Task();

        task.setTaskId(rs.getInt("taskID"));
        task.setTaskName(rs.getString("task_name"));
        task.setSubprojectId(rs.getInt("subProjectId"));
        task.setTaskEstimate(rs.getInt("task_estimate"));
        task.setTaskStartDate(rs.getDate("task_start_date"));
        task.setTaskEndDate(rs.getDate("task_end_date"));
        task.setTaskPriority(rs.getString("task_priority"));
        task.setTaskDescription(rs.getString("task_description"));
        task.setTaskStatus(rs.getString("task_status"));

        return task;
    }

}
