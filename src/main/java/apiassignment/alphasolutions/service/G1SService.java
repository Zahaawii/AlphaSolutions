package apiassignment.alphasolutions.service;


import apiassignment.alphasolutions.model.*;

import apiassignment.alphasolutions.repository.G1SRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<Employee> getProjectAssignees(int projectId) {
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

    public List<SubProject> getSubProjectByProjectId (int id) {
        return g1SRepository.getSubprojectByProjectId(id);
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

    public void deleteTask(int id) {
        g1SRepository.deleteTask(id);
    }

    public void updateTask(Task task) {
        g1SRepository.updateTask(task);
    }

    public void createTask (Task task) {
        g1SRepository.createTask(task);
    }

    public void createSubtask (SubTask subtask) {
        g1SRepository.createSubTask(subtask);
    }

    public Task getTaskById (int id) {
        return g1SRepository.getTaskById(id);
    }

    public SubTask getSubtaskById (int id) {
        return g1SRepository.getSubtaskById(id);
    }

    public void updateSubtask (SubTask subtask) {
        g1SRepository.updateSubtask(subtask);
    }

    public void deleteSubtask(int id) {
        g1SRepository.deleteSubtask(id);
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
    public List<Skill>getAllSkills(){
        return g1SRepository.getAllSkills();
    }

    public List<Task> getTasksBySubprojectId(int id) {
        return g1SRepository.getTasksBySubprojectId(id);
    }

    public List<SubTask> getSubtasksByTaskId(int id) {
        return g1SRepository.getSubtasksByTaskId(id);
    }

    public SubProject getSubProjectById(int id) {
        return g1SRepository.getSubProjectById(id);
    }

    public List<Employee> getEmployeesByTaskId(int id) {
        return g1SRepository.getEmployeesByTaskId(id);
    }

    public List<Employee> getEmployeesBySubtaskId(int id) {
        return g1SRepository.getEmployeesBySubtaskId(id);
    }


    public List<Employee> getEmployeeBySkills(String skills) {
        return g1SRepository.getEmployeeBySkills(skills);
    }

    public List<Employee> getEmployeeNotPartOfProject(int projectId) {
        List<Employee> employeeList = g1SRepository.getEmployeeNotPartOfProject(projectId);
        if(employeeList == null || employeeList.isEmpty()){
            return null;
        } // først henter vi en liste over dem som ikke er i projektet
        for(Employee i: employeeList){ //Så finder vi alle skills hver employee har
            List<Skill>skillList = g1SRepository.getSkillsForEmployee(i.getEmployeeId());
            i.setSkills(skillList); //Så tilføjer vi den givne employees skills til dem
        }
        return employeeList;
    }

    public List<Skill>getSkillsForEmployee(int employeeId){
        return g1SRepository.getSkillsForEmployee(employeeId);
    }

    public List<Employee> getEmployeeBySkillNotPartOfProject(String skill, int projectId) {
        List<Employee> notPartOfProject = g1SRepository.getEmployeeNotPartOfProject(projectId);
        if(notPartOfProject == null || notPartOfProject.isEmpty()){
            return null;
        }
        List<Employee> bySkill = g1SRepository.getEmployeeBySkills(skill);
        if(bySkill == null || bySkill.isEmpty()){
            return null;
        }
        List<Employee> finishList = new ArrayList<>();
        for (Employee i : bySkill) {
            for (Employee b : notPartOfProject) {
                if (i.getEmployeeId() == b.getEmployeeId()) {
                    if (!finishList.contains(i)) {
                        List<Skill> skillList = g1SRepository.getSkillsForEmployee(i.getEmployeeId());
                        i.setSkills(skillList); //finder alle de skills der hører til en person
                        finishList.add(i);
                    } //finder alle dem med en given skill
                } //finder alle dem der ikke er en del af projeketet
            } //sammenligner id på de to lister
        } //hvis den tredje liste(finishList) ikke har objektet i sig,
        //så finder den personens skills, sætter dem på og tilføjer dem til listen
        //man får dermed en liste af folk, som har en given skill og som ikke allerede er tilføjet til projektet
            if(finishList.isEmpty()){
                return null;
            }
        return finishList;
    }

    public void addEmployeeToProject(int projectId, int employeeId) {
        g1SRepository.addEmployeeToProject(projectId, employeeId);
    }

    public List<Project>getProjectsForOneEmployee(int employeeId){
        return g1SRepository.getProjectsForOneEmployee(employeeId);
    }




    public void deleteSubprojectBysubProjectId(int id) {
        g1SRepository.deleteSubProject(id);
    }

    public void updateSubproject(SubProject subProject) {
        g1SRepository.updateSubproject(subProject);
    }

    public Integer getSumOfTaskAndSubTask(int subprojectID) {
        List<Task> tasks = getTasksBySubprojectId(subprojectID);
        Integer sum = 0;

        for(Task task : tasks) {
            if(task.getTaskEstimate() != null) {
                sum += task.getTaskEstimate();
            }

            for(SubTask subTask : task.getSubtasks()) {
                if(subTask.getSubtaskEstimate() != null) {
                    sum += subTask.getSubtaskEstimate();
                }
            }
        }
        return sum;
    }

    public Integer getTotalSumOfProject(int projectID) {
        List<SubProject> subProjects = g1SRepository.getSubprojectByProjectId(projectID);
        Integer sum = 0;

        for(SubProject subProject : subProjects) {
            List<Task> tasks = getTasksBySubprojectId(subProject.getSubprojectID());
            for(Task task : tasks) {
                if(task.getTaskEstimate() != null) {
                    sum += task.getTaskEstimate();
                }

                for(SubTask subTask : task.getSubtasks()) {
                    if(subTask.getSubtaskEstimate() != null) {
                        sum += subTask.getSubtaskEstimate();
                    }
                }
            }
        }
        return sum;
    }

    public List<Project> getAllProjectsWithSum(int employeeID) {
    List<Project> projects = g1SRepository.getAllProjects(employeeID);
    for(Project project : projects) {
        Integer sum = getTotalSumOfProject(project.getProjectId());
        project.setSum(sum);
    }
     return projects;
    }

    public void addAssigneeToTask(int taskId, List<Integer> employeeIds) {
        g1SRepository.addAssigneeToTask(taskId, employeeIds);
    }

    public int getSubprojectIdFromTaskId(int taskId) {
        return g1SRepository.getSubprojectIdFromTaskId(taskId);
    }

    public int getProjectIdFromSubprojectId(int subprojectId) {
        return g1SRepository.getProjectIdFromSubprojectId(subprojectId);
    }

    public List<Integer> getTaskAssignees(int taskId) {
        return g1SRepository.getTaskAssignees(taskId);
    }

    public void clearTaskAssignees(int taskId) {
        g1SRepository.clearTaskAssignees(taskId);
    }
}



