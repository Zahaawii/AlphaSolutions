package apiassignment.alphasolutions.service;

import apiassignment.alphasolutions.model.Project;
import apiassignment.alphasolutions.repository.G1SRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class G1SService {

    private final G1SRepository g1SRepository;

    public G1SService(G1SRepository g1SRepository) {
        this.g1SRepository = g1SRepository;
    }

    public List<Project> getAllProjects(int employeeID) {
        return g1SRepository.getAllProjects(employeeID);
    }

    public void createProject(Project project) {
        g1SRepository.createProject(project);
    }

    public void deleteProject(int projectID) {
        g1SRepository.deleteProject(projectID);
    }

    public void updateProject(Project project) {
        g1SRepository.updateProject(project);
    }
}
