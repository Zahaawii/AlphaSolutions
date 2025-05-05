package apiassignment.alphasolutions.service;

import apiassignment.alphasolutions.model.SubTask;
import apiassignment.alphasolutions.model.Task;
import apiassignment.alphasolutions.repository.G1SRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class G1SService {

    @Autowired
    private G1SRepository g1SRepository;

    public List<Task> getTasksBySubprojectId (int id) {
        return g1SRepository.getTasksBySubprojectId(id);
    }

    public List<SubTask> getSubtasksByTaskId (int id) {
        return g1SRepository.getSubtasksByTaskId(id);
    }
}
