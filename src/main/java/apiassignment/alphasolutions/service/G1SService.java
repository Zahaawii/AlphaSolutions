package apiassignment.alphasolutions.service;

import apiassignment.alphasolutions.model.Employee;
import apiassignment.alphasolutions.model.Project;
import apiassignment.alphasolutions.model.Skill;
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

    public Project getProjectById(int projectId) {
        return g1SRepository.getProjectById(projectId);
    }

    public void createProjectWithAssignees(Project project, List<Integer> assigneeIds) {
        g1SRepository.createProject(project);
        g1SRepository.assignEmployeesToProject(project.getProjectId(), assigneeIds);
    }

    public void updateProjectWithAssignees(Project project, List<Integer> assigneeIds) {
        g1SRepository.updateProject(project);
        g1SRepository.clearProjectAssignees(project.getProjectId());
        g1SRepository.assignEmployeesToProject(project.getProjectId(), assigneeIds);
    }

    public void deleteProject(int projectID) {
        g1SRepository.deleteProject(projectID);
    }

    public List<Employee> getAllEmployees() {
        return g1SRepository.getAllEmployees();
    }

    public List<Integer> getProjectAssignees(int projectId) {
        return g1SRepository.getProjectAssignees(projectId);
    }

    public List<Employee> getAllEmployeeWithSkills() {
        List<Employee> employees = g1SRepository.getAllEmployees();

        for(Employee emp : employees) {
            List<Skill> skills = g1SRepository.getSkillsByEmployeeId(emp.getEmployeeID());
            emp.setSkills(skills);
        }

        return employees;
    }
}
