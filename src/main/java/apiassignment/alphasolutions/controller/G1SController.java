package apiassignment.alphasolutions.controller;


import apiassignment.alphasolutions.DTO.DTOEmployee;
import apiassignment.alphasolutions.model.*;
import apiassignment.alphasolutions.service.G1SEmailService;
import apiassignment.alphasolutions.service.G1SService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Controller
public class G1SController {


    private final G1SService g1SService;

    public G1SController(G1SService g1SService) {
        this.g1SService = g1SService;
    }

    @ModelAttribute
    public void addRoleAttributesToModel(HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");

        if (employee != null) {
            int roleId = employee.getRoleId();
            model.addAttribute("isProjectManager", roleId == 2);
            model.addAttribute("isAdmin", roleId == 3);
            model.addAttribute("canManageProjects", roleId == 2|| roleId == 3);
            model.addAttribute("isEmployee", roleId == 1);
        }
    }

    @GetMapping("")
    public String index (Model model, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/login";
        } else {
            return "redirect:/projects";
        }
    }

    @GetMapping("/projects")
    public String getMyProjects(Model model, HttpSession session) {
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        Employee employee = (Employee) session.getAttribute("employee");
        List <Project> getAllProjects = g1SService.getProjectsWithAssignees(employee.getEmployeeId());

        if (employee == null) {
            return "redirect:/login";
        }

        model.addAttribute("projects", getAllProjects);

        return "myProjects";
    }

    @GetMapping("/projects/new")
    public String newProject(Model model, HttpSession session) {
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/login";
        }
        model.addAttribute("project", new Project());
        return "newProject";
    }

    @PostMapping("/projects")
    public String createProject(@ModelAttribute Project project, HttpSession session) {
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        Employee employee = (Employee) session.getAttribute("employee");

        // Her sættes projektlederen som ejer af projektet.
        project.setEmployeeId(employee.getEmployeeId());

        g1SService.createProject(project);
        return "redirect:/projects";
    }

    @GetMapping("/projects/edit/{id}")
    public String editProject(@PathVariable int id, Model model, HttpSession session) {
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/login";
        }

        List<Employee> assignees = g1SService.getProjectAssignees(id);
        Project project = g1SService.getProjectById(id);
        model.addAttribute("assignees",assignees);
        model.addAttribute("project", project);
        return "editProject";
    }

    @PostMapping("/projects/update")
    public String updateProject(@ModelAttribute Project project, HttpSession session) {
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        System.out.println(project);
        g1SService.updateProject(project);
        return "redirect:/projects";
    }

    @PostMapping("/projects/delete/{id}")
    public String deleteProject(@PathVariable int id, HttpSession session) {
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/login";
        }

        g1SService.deleteProject(id);
        return "redirect:/projects";
    }

    @GetMapping("/project/{id}")
    public String projectView (@PathVariable int id, Model model, HttpSession session) {
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        List<SubProject> subProjectByProjectId = g1SService.getSubProjectByProjectId(id);

        model.addAttribute("project", g1SService.getProjectById(id));
        model.addAttribute("estimate", g1SService.getTotalEstimateOfProject(id));
        model.addAttribute("actual", g1SService.getTotalActualOfProject(id));
        model.addAttribute("predictionRatio", g1SService.getPredictionRatioOfProject(id));
        model.addAttribute("subprojects", subProjectByProjectId);
        model.addAttribute("projectid", id);
        model.addAttribute("completion", g1SService.getProjectCompletion(id));
        model.addAttribute("assignees",g1SService.getProjectAssignees(id));





        return "myProjectSubproject";
    }

    @GetMapping("/project/{id}/assigneesList")
    public String getProjectAssignees(@PathVariable int id, Model model, HttpSession session) {
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/login";
        }
        model.addAttribute("projectId",id);
        model.addAttribute("assignees",g1SService.getProjectAssigneesWithSkills(id));
        return "projectAssignees";
    }

    @PostMapping("/project/{projectId}/assignee/{employeeId}/remove")
    public String removeAssigneeFromProject(@PathVariable int projectId, @PathVariable int employeeId, Model model, HttpSession session) {
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        g1SService.removeAssigneeFromProject(projectId,employeeId);
        return "redirect:/project/" + projectId + "/assigneesList";
    }

    @GetMapping("/subprojects")
    public String subProjects(Model model) {
        List<SubProject> getAllSubProjects = g1SService.getAllSubProjects();
        model.addAttribute("AllSubProjects", getAllSubProjects);
        return "subprojects";
    }

    @GetMapping("/create/subproject/{id}")
    public String createSubproject(Model model, @PathVariable int id, HttpSession session) {
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        SubProject subProject = new SubProject();
        subProject.setProjectID(id);
        model.addAttribute("subproject", subProject);
        return "createSubproject";
    }

     @PostMapping("/create/subproject")
     public String addSubproject(@ModelAttribute SubProject subProject, HttpSession session) {
         if(!g1SService.isLoggedIn(session)){
             return "redirect:/login";
         }
         System.out.println(subProject);
        g1SService.addSubproject(subProject);
        return "redirect:/project/" + subProject.getProjectID();
     }


    @GetMapping("/login")
    public String login(){
        return "login";
    }


    @PostMapping("/login")
    public String checkLogin(@RequestParam("checkUsername") String username, @RequestParam("checkUserpassword") String password,
                             HttpSession session, Model model){
        Employee employee = g1SService.findByUsername(username);

        if(employee == null ||  !g1SService.decryptTest(password, employee.getEmployeePassword())  ){
            model.addAttribute("wrongLogin", true);
            return "login";
        }
        session.setAttribute("employee", employee);
        session.setMaxInactiveInterval(3600);
        return "redirect:/projects";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "login";
    }




    @PostMapping("/subproject/delete/{id}")
    public String deleteSubprojectBySubprojectId(@PathVariable int id, HttpSession session) {
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        System.out.println(g1SService.getSubProjectById(id));
        int projectid = g1SService.getSubProjectById(id).getProjectID();
        g1SService.deleteSubprojectBysubProjectId(id);
        return "redirect:/project/" + projectid;
    }


    @GetMapping("/subproject/{id}")
    public String subProjectView (@PathVariable("id") int subprojectId, Model model, HttpSession session) {
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        List<Task> tasks = g1SService.getTasksBySubprojectId(subprojectId);

        //loop igennem alle tasks og sæt assignees til deres respektive task. Samme sker med subtasks.
        for (Task task : tasks) {
            task.setAssignees(g1SService.getEmployeesByTaskId(task.getTaskId()));

            for (SubTask subtask : task.getSubtasks()) {
                subtask.setAssignees(g1SService.getEmployeesBySubtaskId(subtask.getSubtaskID()));
            }
        }

        model.addAttribute("tasks", tasks);
        SubProject subproject = g1SService.getSubProjectById(subprojectId);
        model.addAttribute("subproject", subproject);

        return "subprojectview";
    }


    @GetMapping("/adminpanel")
    public String adminPanel(HttpSession session, Model model){
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        Employee checkEmployee = (Employee) session.getAttribute("employee");
        if(checkEmployee.getRoleId() != 2 && checkEmployee.getRoleId() != 3){
            return "redirect:/projects";
        }
        //projekleder(role 2) kan kun se medarbejdere
        if(checkEmployee.getRoleId() == 2){
            List<Employee>getAllCommonWorkers = g1SService.getAllCommonWorkersPlusSkills();
            model.addAttribute("getAllEmployee", getAllCommonWorkers);
        }
        //admins(role 3) kan se alle, medarbejdere, projektledere og admins
        if(checkEmployee.getRoleId() == 3) {
            List<Employee> getAllEmployee = g1SService.getAllEmployeePlusSkills();
            model.addAttribute("getAllEmployee", getAllEmployee);
        }
        List<Skill> skillList = g1SService.getAllSkills();
        model.addAttribute("skillList", skillList);
        return "adminPanel";
    }

    @GetMapping("/admin/addEmployee")
    public String adminPanelAddEmployee(HttpSession session, Model model){
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        Employee checkEmployee = (Employee)session.getAttribute("employee");
        //hvis en employee ikke er role 3(admin) eller 2(projektleder), så bliver de redirectet væk fra siden
        if(checkEmployee.getRoleId() != 3 && checkEmployee.getRoleId() != 2){
            return "redirect:/projects";
        }
        //tilføjer et nyt employee objekt
        DTOEmployee employee = new DTOEmployee();
        //hvis den givne employee er role 2(projektleder) så tilgår de siden
        //projektledere kan kun oprette medarbejdere og ikke projektledere og admins
        if(checkEmployee.getRoleId() == 2) {
            employee.setRoleId(1);
        }
        if(checkEmployee.getRoleId() == 3) {
            //Hvis den givne employee er role 3(admin), så tilgår de siden og har mulighed for at ændre roleID
            model.addAttribute("admin", true);
            List<Role> listOfRoles = g1SService.getAllRoles();
            model.addAttribute("roles", listOfRoles);
            List<Skill> skillList = g1SService.getAllSkills();
            model.addAttribute("skillList", skillList);
        }
        model.addAttribute("employee", employee);
        return "adminAddEmployee";
    }

    @PostMapping("/admin/register")
    public String adminRegisterEmployee(@ModelAttribute DTOEmployee employee, Model model, HttpSession session){
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        if(!g1SService.isUsernameFree(employee.getEmployeeUsername(), employee.getEmployeeId()) || !g1SService.isUsernameAwaitingUserFree(employee.getEmployeeUsername(), employee.getEmployeeId())){ //tjekker om brugernavnet er frit
            model.addAttribute("notFree", true);
            return "redirect:/admin/addEmployee";
        }
        employee.setEmployeePassword(g1SService.encryptTest(employee.getEmployeePassword()));
        g1SService.adminRegisterEmployee(employee);
        return "redirect:/adminpanel";
    }

    @PostMapping("/admin/delete/{id}")
    public String adminDeleteEmployee(@PathVariable int id, HttpSession session){
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        g1SService.deleteEmployee(id);
        return "redirect:/adminpanel";
    }

    @PostMapping("/subproject/{subprojectid}/delete/subtask/{subtaskid}")
    public String deleteSubtask (@PathVariable int subprojectid, @PathVariable int subtaskid, HttpSession session) {
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        Employee employee = (Employee) session.getAttribute("employee");

        String subprojectIdString = String.valueOf(subprojectid);
        g1SService.deleteSubtask(subtaskid);
        return "redirect:/subproject/" + subprojectIdString;
    }

    @PostMapping("/subproject/{subprojectid}/delete/task/{taskid}")
    public String deleteTask (@PathVariable int subprojectid, @PathVariable int taskid, HttpSession session) {
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        String subprojectIdString = String.valueOf(subprojectid);
        g1SService.deleteTask(taskid);
        return "redirect:/subproject/" + subprojectIdString;
    }


    @GetMapping("/subproject/{subprojectid}/edit/task/{taskid}")
    public String editTask (@PathVariable int subprojectid, @PathVariable int taskid, Model model, HttpSession session) {
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        model.addAttribute("task", g1SService.getTaskById(taskid));
        model.addAttribute("subprojectid", subprojectid);
        model.addAttribute("taskid", taskid);
        model.addAttribute("assigned",g1SService.getTaskAssignees(taskid));

        int projectId = g1SService.getProjectIdFromSubprojectId(subprojectid);
        model.addAttribute("projectAssignees", g1SService.getProjectAssignees(projectId));
        return "editTask";
    }

    @PostMapping("/subproject/{subprojectid}/edit/task/{taskid}")
    public String editTask(@PathVariable int subprojectid, @PathVariable int taskid, @ModelAttribute Task task,
                           @RequestParam(required = false) List<Integer> employeeIds, HttpSession session) {
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }

        g1SService.updateTask(task);

        g1SService.clearTaskAssignees(taskid);
        if (employeeIds != null && !employeeIds.isEmpty()) {
            g1SService.addAssigneeToTask(taskid, employeeIds);
        }

        return "redirect:/subproject/" + subprojectid;
    }

    @GetMapping("/subproject/{subprojectid}/create/task")
    public String createTask(@PathVariable int subprojectid, Model model, HttpSession session) {
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        model.addAttribute("subprojectid", subprojectid);
        model.addAttribute("task", new Task());

        int projectId = g1SService.getProjectIdFromSubprojectId(subprojectid);
        model.addAttribute("projectAssignees", g1SService.getProjectAssignees(projectId));


        return "createTask";
    }

    @PostMapping("/subproject/{subprojectid}/create/task")
    public String createTask(@PathVariable int subprojectid, Task task,
                             @RequestParam(required = false) List<Integer> employeeIds, HttpSession session) {
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        task.setSubprojectId(subprojectid);
        g1SService.createTask(task);

        if (employeeIds != null && !employeeIds.isEmpty()) {
            g1SService.addAssigneeToTask(task.getTaskId(), employeeIds);
        }

        return "redirect:/subproject/" + subprojectid;
    }

    @GetMapping("/subproject/{subprojectid}/task/{taskid}/create/subtask")
    public String createSubtask(@PathVariable int subprojectid, @PathVariable int taskid, Model model, HttpSession session ) {
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        model.addAttribute("subprojectid", subprojectid);
        model.addAttribute("taskid", taskid);
        model.addAttribute("subtask", new SubTask());

        int projectId = g1SService.getProjectIdFromSubprojectId(subprojectid);
        model.addAttribute("projectAssignees", g1SService.getProjectAssignees(projectId));
        System.out.println("THIS IS THE TASKID: " + taskid);
        return "createSubtask";
    }

    @PostMapping("/subproject/{subprojectid}/task/{taskid}/create/subtask")
    public String createSubtask(@PathVariable int subprojectid, @PathVariable int taskid, SubTask subtask,
                                @RequestParam(required = false) List<Integer> employeeIds, HttpSession session) {
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        subtask.setTaskID(taskid);
        g1SService.createSubtask(subtask);

        if (employeeIds != null && !employeeIds.isEmpty()) {
            g1SService.addAssigneeToSubtask(subtask.getSubtaskID(), employeeIds);
        }

        return "redirect:/subproject/" + subprojectid;
    }

    @GetMapping("/subproject/{subprojectid}/edit/subtask/{subtaskid}")
    public String editSubtask (@PathVariable int subprojectid, @PathVariable int subtaskid, Model model, HttpSession session) {
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        model.addAttribute("subtask", g1SService.getSubtaskById(subtaskid));
        model.addAttribute("subprojectid", subprojectid);
        model.addAttribute("subtaskid", subtaskid);
        model.addAttribute("assigned",g1SService.getSubtaskAssignees(subtaskid));

        int projectId = g1SService.getProjectIdFromSubprojectId(subprojectid);
        model.addAttribute("projectAssignees", g1SService.getProjectAssignees(projectId));

        return "editSubtask";
    }

    @PostMapping("/subproject/{subprojectid}/edit/subtask/{subtaskid}")
    public String editSubtask(@PathVariable int subprojectid, @PathVariable int subtaskid,
                              @ModelAttribute SubTask subtask, @RequestParam(required = false) List<Integer> employeeIds, HttpSession session) {
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        g1SService.updateSubtask(subtask);

        g1SService.clearSubtaskAssignees(subtaskid);
        if (employeeIds != null && !employeeIds.isEmpty()) {
            g1SService.addAssigneeToSubtask(subtaskid, employeeIds);
        }

        return "redirect:/subproject/" + subprojectid;
    }

    @GetMapping("/admin/update/{id}")
    public String adminUpdateEmployeeGet(@PathVariable int id, HttpSession session, Model model){
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        Employee checkEmployee = (Employee)session.getAttribute("employee");
        //hvis en employee ikke er role 3(admin) eller 2(projektleder), så bliver de redirectet væk fra siden
        if(checkEmployee.getRoleId() != 3 && checkEmployee.getRoleId() != 2){
            return "redirect:/projects";
        }
        //laver et employee objekt ud fra det id vi får med i URL'en
        Employee oldEmployee = g1SService.getEmployeeByIdPlusSkills(id);
        model.addAttribute("oldEmployee", oldEmployee);
        //tilføjer et nyt employee objekt
        DTOEmployee newEmployee = new DTOEmployee();
        //hvis den givne employee er role 2(projektleder) så tilgår de siden
        //projektledere kan kun oprette medarbejdere og ikke projektledere og admins
        if(checkEmployee.getRoleId() == 2) {
            newEmployee.setRoleId(1);
        }
        //Hvis den givne employee er role 3(admin), så tilgår de siden og har mulighed for at ændre roleID
        if(checkEmployee.getRoleId() == 3) {
            model.addAttribute("admin", true);
            List<Role> listOfRoles = g1SService.getAllRoles();
            model.addAttribute("roles", listOfRoles);
        }
        List<Skill> skillList = g1SService.getAllSkills();
        model.addAttribute("skillList", skillList);
        model.addAttribute("newEmployee", newEmployee);
        return "adminUpdateEmployee";
    }

    @PostMapping("/admin/update")
    public String adminUpdateEmployeePost(@ModelAttribute DTOEmployee newEmployee, HttpSession session, Model model){
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        if(!g1SService.isUsernameFree(newEmployee.getEmployeeUsername(), newEmployee.getEmployeeId()) || !g1SService.isUsernameAwaitingUserFree(newEmployee.getEmployeeUsername(), newEmployee.getEmployeeId())){ //tjekker om brugernavnet er frit
            model.addAttribute("notFree", true);
            return "redirect:/admin/update/" + newEmployee.getEmployeeId(); //hvis det ikke er frit, bliver man smidt tilbage til update siden
        }
        newEmployee.setEmployeePassword(g1SService.encryptTest(newEmployee.getEmployeePassword()));
        g1SService.updateEmployee(newEmployee);
        return "redirect:/adminpanel";
    }

    //nedenstående metode, modtager en pathvariable(projectId) og en requestParam(skill), den redirecter så en til
    //project/projectId/assignees?skill=xxx" her bliver den valgte skill så URL-encoded, så den ikke forvirrer selve url'en
    //dette er gjort, fordi den tidligere crashede, da man prøvede at tilgå "UI/UX", fordi den opfattede det som et nyt endpoint pga "/"
    @GetMapping("/project/{projectId}/assignees/select-skill")
    public String redirectToSkill(
            @PathVariable Long projectId,
            @RequestParam String skill) {
        return "redirect:/project/" + projectId + "/assignees?skill=" + UriUtils.encode(skill, StandardCharsets.UTF_8);
    }

    @GetMapping("/project/{projectId}/assignees")
    public String getEmployees(
            @PathVariable int projectId,
            @RequestParam(required = false) String skill,
            Model model, HttpSession session) {
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        //henter en liste med all skills og tilføjer dem til model
        List<Skill> skillList = g1SService.getAllSkills();
        model.addAttribute("skills", skillList);
        model.addAttribute("projectId", projectId);
        //henter projectId, altså det projekt vi er i gang med og hvor vi vil tilføje employees
        List<Employee> employees; // laver en tom liste af employee
        if (skill != null && !skill.isEmpty()) { // hvis skill ikke er null eller empty,
            // så finder vi alle dem der ikke er en del af projektet og som har en given skill
            employees = g1SService.getAllEmployeesWithSkillNotPartOfProject(skill, projectId);
            if (employees == null || employees.isEmpty()) {
                //hvis den liste vi får tilbage ikke indeholder nogle empployee,
                // så kom der en tekst om der siger, at den ikke kunne finde nogen med denne skill
                //derudover kommer vores tabel med employees heller ikke op
                model.addAttribute("noEmployee", true);
                model.addAttribute("foundEmployee", false);
                return "employeesWithSkill";
            }
        } else {
            //hvis vi ikke angav en given skill, fx vi trykkede "all employee"
            //Så får vi en liste af alle dem der ikke er en del af projektet i forvejen
            employees = g1SService.getAllEmployeesWithSkillNotPartOfProject(skill, projectId);
            //så tjekker vi om denne liste er tom for employees
            if (employees == null || employees.isEmpty()) {
                //hvis vi ikke finde nogen, så kommer der en tekst, der siger, at den ikke kunne finde nogen
                //måske er de allerede en del af projektet
                model.addAttribute("notPartOfProject", true);
                model.addAttribute("foundEmployee", false);
                return "employeesWithSkill";
            }
        }
        //vi er nu kommet forbi de to foregående if statemens, og vi ved nu, at vi har en liste af employee som ikke er tom
        //derfor gør vi at vi kan se vores tabel og dets employees
        model.addAttribute("foundEmployee", true);
        //nu tilføjer vi så listen af employees, den indeholder så enten kun dem der ikke er en del af projektet
        //Eller dem som ikke er en del af projektet og har en specifik skill såsom java
        model.addAttribute("listOfEmployee", employees);
        return "employeesWithSkill";
    }



    @PostMapping("/project/{projectId}/add/{employeeId}")
    public String addEmployeeToProject( @PathVariable int projectId, @PathVariable int employeeId, HttpSession session, Model model){
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        if(projectId == 0 || employeeId == 0){
            //der mangler lige en "model.addAttribute("notAdded", true),
            // som retunere en til projekt siden og siger man ikke kunne tilføje den givne empployee
            return "redirect:/project/" + projectId;
        }
        g1SService.addEmployeeToProject(projectId, employeeId);
        return "redirect:/project/" + projectId + "/assignees";
    }

    @GetMapping("/profile/{employeeId}")
    public String seeProfile(@PathVariable int employeeId, HttpSession session, Model model){
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        Employee employee = g1SService.getEmployeeById(employeeId);
        employee.setSkills(g1SService.getSkillsForEmployee(employeeId));
        List<Project>projectList = g1SService.getProjectsForOneEmployee(employeeId);
        model.addAttribute("employee", employee);
        model.addAttribute("projects", projectList);
        return "profile";
    }


    @GetMapping("/mySubTasks/sortBy")
    public String sortMyTasks(@RequestParam(required = false) String chosen, HttpSession session, Model model){
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        } //sorterer en employees subtask ud for en given faktorer, såsom prioritet
        Employee employee = (Employee)session.getAttribute("employee");
        List<SubTask>subTaskList = g1SService.getSortedSubtaskByEmployeeId(chosen, employee.getEmployeeId());
        if(subTaskList == null || subTaskList.isEmpty()){
            model.addAttribute("noSubTasks", true);
            model.addAttribute("found", false);
            return "mySubTasks";
        }
        model.addAttribute("found", true);
        model.addAttribute(subTaskList);
        return "mySubTasks";
    }


    @GetMapping("/subproject/edit/{id}")
    public String editSubProject(@PathVariable int id, Model model, HttpSession session) {
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/login";
        }

        SubProject subProject = g1SService.getSubProjectById(id);
        model.addAttribute("subproject", subProject);
        return "editSubprojects";
    }

    @PostMapping("/subprojects/update")
    public String updateSubProject(@ModelAttribute SubProject subProject, HttpSession session) {
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        g1SService.updateSubproject(subProject);
        return "redirect:/project/" + subProject.getProjectID();
    }

    @GetMapping("/create/user")
    public String createUser(Model model) {
        AwaitingEmployee employee = new AwaitingEmployee();
        model.addAttribute("employee", employee);

        return "createUser";
    }

    @PostMapping("/create/user")
    public String createUserPost(@ModelAttribute AwaitingEmployee employee) {
        if(!g1SService.isUsernameFree(employee.getAwaitingEmployee_name(), employee.getAwaitingEmployeeID()) || !g1SService.isUsernameAwaitingUserFree(employee.getAwaitingEmployee_name(), employee.getAwaitingEmployeeID())) {
            return "redirect:/create/user";
        }
        employee.setAwaitingEmployee_password(g1SService.encryptTest(employee.getAwaitingEmployee_password()));
        g1SService.createUser(employee);
        g1SService.sendEmail(employee.getAwaitingEmployee_name());
        return "redirect:/login";
    }

    @GetMapping("/awaitingusers")
    public String getAllAwaitingUsers(Model model, HttpSession session) {
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        List<AwaitingEmployee> getAll = g1SService.getAllAwaitingUsers();
        model.addAttribute("getAll", getAll);
        return "awaitinguserslist";
    }

    @PostMapping("/create/user/adminApproval")
    public String adminApprovedEmployee(@ModelAttribute DTOEmployee employee, HttpSession session) {
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        g1SService.adminRegisterEmployee(employee);
        System.out.println(employee.getEmployeeUsername());
        g1SService.deleteAwaitingEmployeeWithUsername(employee.getEmployeeUsername());
        return "redirect:/awaitingusers";
    }

    @PostMapping("/delete/user/admindenied/{id}")
    public String adminDenied(@PathVariable int id, HttpSession session) {
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        g1SService.deleteAwaitingEmployee(id);
        return "redirect:/awaitingusers";
    }

    @GetMapping("/admin/skillsPanel")
    public String skillsPanel(HttpSession session, Model model){
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        List<Skill>skillList = g1SService.getAllSkills();
        model.addAttribute("skillList", skillList);
        Skill skill = new Skill();
        model.addAttribute("skill", skill);
        return "skillsPanel";
    }



    @PostMapping("/admin/skillsPanel/delete/{skillId}")
    public String deleteSkill(@PathVariable int skillId, HttpSession session){
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        g1SService.deleteSkill(skillId);

        return "redirect:/admin/skillsPanel";
    }


    @PostMapping("/admin/skillsPanel/create")
    public String createSkill(@ModelAttribute Skill skill, HttpSession session, Model model){
        if(!g1SService.isLoggedIn(session)){
            return "redirect:/login";
        }
        if(!g1SService.skillNotInDb(skill)){
            model.addAttribute("alreadyInDb", true);
            return "redirect:/admin/skillsPanel";
        }
        g1SService.createSkill(skill);
        return "redirect:/admin/skillsPanel";
    }

}
