package apiassignment.alphasolutions.service;


import apiassignment.alphasolutions.model.*;

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

    public void createProject(Project project) {
        g1SRepository.createProject(project);
    }

    public void updateProject(Project project) {
        g1SRepository.updateProject(project);
    }

    public void deleteProject(int projectID) {
        g1SRepository.deleteProject(projectID);
    }

    public List<Employee> getAllEmployees() {
        return g1SRepository.getAllEmployee();
    }

    public List<Integer> getProjectAssignees(int projectId) {
        return g1SRepository.getProjectAssignees(projectId);
    }

    public List<Employee> getAllEmployeeWithSkills() {
        List<Employee> employees = g1SRepository.getAllEmployee();

        for(Employee emp : employees) {
            List<Skill> skills = g1SRepository.getSkillsByEmployeeId(emp.getEmployeeId());
            emp.setSkills(skills);
        }

        return employees;
    }

    public List<Employee> getEmployeesByIds(List<Integer> ids) {
        return g1SRepository.getEmployeesByIds(ids);
    }


    public List<SubProject> getAllSubProjects() {
        return g1SRepository.getAllSubProjects();
    }

    public SubProject addSubproject(SubProject subProject) {
        return g1SRepository.addSubProject(subProject);
    }
    public Employee login(String username, String password) {
        return g1SRepository.login(username, password);
    }

    public List<Employee> getAllEmployee() {
        return g1SRepository.getAllEmployee();
    }

    public List<Employee> getAllCommonWorkers() {
        return g1SRepository.getAllCommonWorkers();
    }

    public boolean isUsernameFree(String username) {
        return g1SRepository.isUsernameFree(username);
    }

    public Employee adminRegisterEmployee(Employee employee) {
        return g1SRepository.adminRegisterEmployee(employee);
    }

    public void deleteEmployee(int id) {
        g1SRepository.deleteEmployee(id);
    }

    public Employee getEmployeeById(int id) {
        return g1SRepository.getEmployeeById(id);
    }

    public Employee updateEmployee(Employee employee) {
        return g1SRepository.updateEmployee(employee);
    }

    public List<Role> getAllRoles() {
        return g1SRepository.getAllRoles();
    }


    public List<Task> getTasksBySubprojectId(int id) {
        return g1SRepository.getTasksBySubprojectId(id);
    }

    public List<SubTask> getSubtasksByTaskId(int id) {
        return g1SRepository.getSubtasksByTaskId(id);
    }

    public SubProject getSubProjectById (int id) {
        return g1SRepository.getSubProjectById(id);
    }
    public List<Employee> getEmployeesByTaskId (int id) {
        return g1SRepository.getEmployeesByTaskId(id);
    }
    public List<Employee> getEmployeesBySubtaskId (int id) {
        return g1SRepository.getEmployeesBySubtaskId(id);
    }

}



