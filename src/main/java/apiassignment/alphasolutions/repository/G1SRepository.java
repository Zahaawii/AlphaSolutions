package apiassignment.alphasolutions.repository;

import apiassignment.alphasolutions.model.SubTask;
import apiassignment.alphasolutions.model.Task;
import apiassignment.alphasolutions.rowmapper.SubTaskRowMapper;
import apiassignment.alphasolutions.rowmapper.TaskRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class G1SRepository {

    private JdbcTemplate jdbcTemplate;

    public G1SRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Task> getTasksBySubprojectId(int id) {
        String sql = "SELECT * FROM task WHERE subProjectId = ?";
        List<Task> tasks = jdbcTemplate.query(sql, new TaskRowMapper(), id);

        for (Task task : tasks) {
            task.setSubtasks(getSubtasksByTaskId(task.getTaskId()));
        }

        return tasks;
    }

    public List<SubTask> getSubtasksByTaskId(int id) {
        String sql = "SELECT * FROM subtask WHERE taskID = ?";
        return jdbcTemplate.query(sql, new SubTaskRowMapper(), id);
    }

}
