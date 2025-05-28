package apiassignment.alphasolutions.service;


import apiassignment.alphasolutions.DTO.DTOEmployee;
import apiassignment.alphasolutions.model.*;

import apiassignment.alphasolutions.repository.G1SRepository;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class G1SService {

    private final G1SRepository g1SRepository;

    public G1SService(G1SRepository g1SRepository) {
        this.g1SRepository = g1SRepository;
    }

    public boolean isLoggedIn(HttpSession session){
        return session.getAttribute("employee") != null;
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

    public int getProjectCompletion(int projectid) {

        List<SubTask> subtasks = g1SRepository.getAllSubtasksByProjectId(projectid);

        int subtaskcount = subtasks.size();
        int subtaskscomplete = 0;

        for (SubTask subtask : subtasks) {
            if (subtask.getSubtaskStatus().equalsIgnoreCase("Completed")) {
                subtaskscomplete++;
            }
        }

        return Math.round(((float) subtaskscomplete / subtaskcount) * 100);
    }




    public List<Employee> getProjectAssignees(int projectId) {
        return g1SRepository.getProjectAssignees(projectId);
    }

    public List<Employee> getProjectAssigneesWithSkills(int projectId) {
        List<Employee> employees = getProjectAssignees(projectId);
        for (Employee emp : employees) {
            emp.setSkills(getSkillsForEmployee(emp.getEmployeeId()));
        }
        return employees;
    }

    public void removeAssigneeFromProject(int projectId, int employeeId) {
        g1SRepository.removeAssigneeFromProject(projectId,employeeId);
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

    public List<Employee>getAllEmployeePlusSkills(){
        List<Employee>employeeList = g1SRepository.getAllEmployee();
        if(employeeList == null || employeeList.isEmpty()){
            return null;
        }
        for(Employee i: employeeList){
            List<Skill> skillList = g1SRepository.getSkillsForEmployee(i.getEmployeeId());
            i.setSkills(skillList);
        }
        return employeeList;
    }

    public List<Employee> getAllCommonWorkersPlusSkills(){
        List<Employee>employeeList = g1SRepository.getAllCommonWorkers();
        if(employeeList == null || employeeList.isEmpty()){
            return null;
        }
        for(Employee i: employeeList){
            List<Skill>skillList = g1SRepository.getSkillsForEmployee(i.getEmployeeId());
            i.setSkills(skillList);
        }
        return employeeList;
    }

    public boolean isUsernameFree(String employee, int id) {
        return g1SRepository.isUsernameFree(employee, id);
    }

    public DTOEmployee adminRegisterEmployee(DTOEmployee employee) {
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
    public Employee getEmployeeByIdPlusSkills(int id){
        Employee employee = g1SRepository.getEmployeeById(id);
        List<Skill> skillList = g1SRepository.getSkillsForEmployee(id);
        employee.setSkills(skillList);
        return employee;
    }

    public DTOEmployee updateEmployee(DTOEmployee employee) {
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

    public SubProject getSubProjectById(int id) {
        return g1SRepository.getSubProjectById(id);
    }

    public List<Employee> getEmployeesByTaskId(int id) {
        return g1SRepository.getEmployeesByTaskId(id);
    }

    public List<Employee> getEmployeesBySubtaskId(int id) {
        return g1SRepository.getEmployeesBySubtaskId(id);
    }



    public List<Skill>getSkillsForEmployee(int employeeId){
        return g1SRepository.getSkillsForEmployee(employeeId);
    }

    public List<Employee> getAllEmployeesWithSkillNotPartOfProject(String skill, int projectId){
        return g1SRepository.getAllEmployeesWithSkillNotPartOfProject(skill, projectId);
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


    public Integer getTotalEstimateOfProject(int projectID) {
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

    public int getTotalActualOfProject(int projectID) {
        List<SubTask> subtasks = g1SRepository.getAllSubtasksByProjectId(projectID);
        int actualHours = 0;
        for (SubTask subtask : subtasks) {
            actualHours += subtask.getSubtaskHoursSpent();
        }

        return actualHours;
    }

    public double getPredictionRatioOfProject(int projectID) {
        double actual = getTotalActualOfProject(projectID);
        double estimate = getTotalEstimateOfProject(projectID);

        if (actual / estimate == 0) return 0.00;


        double ratio = (estimate / actual);

        return Math.round(ratio * 100.0) / 100.0;

    }




    public void addAssigneeToTask(int taskId, List<Integer> employeeIds) {
        g1SRepository.addAssigneeToTask(taskId, employeeIds);
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

    public void addAssigneeToSubtask(int subtaskId, List<Integer> employeeIds) {
        g1SRepository.addAssigneeToSubtask(subtaskId,employeeIds);
    }

    public List<Integer> getSubtaskAssignees(int subtaskId) {
        return g1SRepository.getSubtaskAssignees(subtaskId);
    }

    public void clearSubtaskAssignees(int subtaskId) {
        g1SRepository.clearSubtaskAssignees(subtaskId);
    }
    public String encryptPassword(String password) {
        String salt = BCrypt.gensalt(10);
        return BCrypt.hashpw(password, salt);
    }

    public boolean verifyPassword(String password, String encrypted) {
        return BCrypt.checkpw(password, encrypted);
    }

    public Employee findByUsername(String username) {
        return g1SRepository.findByUsername(username);

    }

    public AwaitingEmployee createUser(AwaitingEmployee employee) {
        return g1SRepository.createUser(employee);
    }

    public List<AwaitingEmployee> getAllAwaitingUsers() {
        return g1SRepository.getAllAwaitingUsers();
    }

    public void deleteAwaitingEmployee(int id) {
        g1SRepository.deleteAwaitingEmployee(id);
    }


    public void deleteAwaitingEmployeeWithUsername(String username) {
        g1SRepository.deleteAwaitingEmployeeWithUsername(username);
    }

    public void sendEmail(String employee) throws IOException{
        var emailService = new G1SEmailService();
        var body = "Hej, der er en bruger ved navn: " + employee + " som har anmodet om at blive oprettet i systemet." +
                " Du kan se brugeren herinde: https://g1ssolutions.azurewebsites.net/awaitingusers" ;

        emailService.sendEmail("Zahaawii@gmail.com", "Ny bruger anmodet om adgang", body);
    }

    public boolean isUsernameAwaitingUserFree(String username, int id) {
        return g1SRepository.isUsernameAwaitingUserFree(username, id);
    }

    public List<Project> getProjectsWithAssignees(int empId) {
        return g1SRepository.getProjectsWithAssignees(empId);
    }

    public List<SubTask>getSortedSubtaskByEmployeeId(String sorted, int employeeId){
        return g1SRepository.getSortedSubtaskByEmployeeIdPerfected(sorted, employeeId);
    }
    public void deleteSkill(int skillId){
        g1SRepository.deleteSkill(skillId);
    }

    public Skill createSkill(Skill skill){
        return g1SRepository.createSkill(skill);
    }
    public boolean skillNotInDb(Skill skill){
        return g1SRepository.skillNotInDb(skill);
    }

    }




