package apiassignment.alphasolutions.service;

import apiassignment.alphasolutions.model.SubProject;
import apiassignment.alphasolutions.repository.G1SRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class G1SService {


    private final G1SRepository g1SRepository;

    public G1SService(G1SRepository g1SRepository) {
        this.g1SRepository = g1SRepository;
    }

    public List<SubProject> getAllSubProjects() {
        return g1SRepository.getAllSubProjects();
    }

    public SubProject addSubproject(SubProject subProject) {
        return g1SRepository.addSubProject(subProject);
    }
}
