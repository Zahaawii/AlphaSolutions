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
        return g1SRepository.getEmployeeNotPartOfProject(projectId);
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




}


