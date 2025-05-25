package apiassignment.alphasolutions;


import apiassignment.alphasolutions.DTO.DTOEmployee;
import apiassignment.alphasolutions.controller.G1SController;
import apiassignment.alphasolutions.model.*;
import apiassignment.alphasolutions.service.G1SService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(G1SController.class)
public class controllerTest {

    //Adding test objects to testing page, so we can test our controllers with sesison
    Employee employee;
    DTOEmployee dtoEmployee;
    MockHttpSession session;
    Project project;
    SubProject subProject;
    Skill skill;
    SubTask subtask;
    Employee oldEmployee;

    //Calling MockMvc method to test our controllers
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private G1SService g1SService;

    //Before each run the code will setup these variables to be able to test
    @BeforeEach
    void setup() {
        employee = new Employee(1, "test","test","test","test",null, 3);
        dtoEmployee = new DTOEmployee(1, "test","test","test","test",null, 3);
        session = new MockHttpSession();
        session.setAttribute("employee", employee);
        project = new Project(1, "Project test", 1,null, null, "test", "igang");
        subProject = new SubProject(1, "subProject test", null,null,1);
        skill = new Skill(1, "Frontend");
        subtask = new SubTask();
        oldEmployee = new Employee(1, "test","test","test","test",null, 3);


    }

    @AfterEach
    void tearDown() {

    }

//    //Testing to see if you land on our homepage
//    @Test
//    void homepage() throws Exception {
//        mockMvc.perform(get(""))
//                .andExpect(status().isOk())
//                .andExpect(view().name("homepage"));
//    }

    //Testing if you get redirected if you are not logged in
    @Test
    void projects() throws Exception {
        mockMvc.perform(get("/projects"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    //Testing if the if statement works and you access my projects if seesion is viable
    @Test
    void projectsLoggedIn() throws Exception {
        when(g1SService.isLoggedIn(session)).thenReturn(true);
        mockMvc.perform(get("/projects").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("myProjects"));
    }

    //Testing if you get redirected to login page
    @Test
    void createProject() throws Exception {
        mockMvc.perform(get("/projects/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    //Testing if you access newProject if you are logged in
    @Test
    void createProjectLoggedIn() throws Exception {
        when(g1SService.isLoggedIn(session)).thenReturn(true);
        mockMvc.perform(get("/projects/new").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("newProject"));
    }

    //Testing if you get redirected to login page
    @Test
    void editProject() throws Exception {

        mockMvc.perform(get("/projects/edit/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    //Testing if you can access edit project when logged in
    @Test
    void editProjectLoggedIn() throws Exception {

       //By using when, we tell the test, that our service that it works by returning our test data
        when(g1SService.getProjectById(project.getProjectId())).thenReturn(project);
        when(g1SService.isLoggedIn(session)).thenReturn(true);
        mockMvc.perform(get("/projects/edit/" + project.getProjectId()).session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("editProject"));
    }

    //Testing if you can update a project
    @Test
    void updateProject() throws Exception {
        when(g1SService.isLoggedIn(session)).thenReturn(true);
        mockMvc.perform(post("/projects/update").session(session).
                param("projectId", "1")
                .param("projectName", "test")
                .param("projectDescription","testDesc")
                .param("projectStartDate", "2025-05-10")
                .param("projectEndDate", "2025-05-10")
                .param("employeeId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"));
    }

    //Testing if you can delete a project
    @Test
    void deleteProject() throws Exception {
        when(g1SService.isLoggedIn(session)).thenReturn(true);
        mockMvc.perform(post("/projects/delete/1").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"));
    }

//    //Method decapriated
//    @Test
//    void viewSubprojects () throws Exception {
//
//        List<SubProject> testList = List.of(subProject);
//        when(g1SService.isLoggedIn(session)).thenReturn(true);
//        when(g1SService.getSubProjectByProjectId(1)).thenReturn(testList);
//        mockMvc.perform(get("/project/1").session(session))
//                .andExpect(status().isOk())
//                .andExpect(view().name("myprojectSubproject"));
//
//    }

//    @Test
//    void selectCollaborators() throws Exception {
//        when(g1SService.isLoggedIn(session)).thenReturn(true);
//        mockMvc.perform(get("/select-collaborators").session(session))
//                .andExpect(status().isOk())
//                .andExpect(view().name("selectCollaborators"));
//    }

    //Testing if you can see all subprojects that has been mocked
    @Test
    void getAllSubProjects() throws Exception {
        List<SubProject> testList = List.of(subProject,
                new SubProject(5,"2", null, null,3));
        when(g1SService.getAllSubProjects()).thenReturn(testList);
        when(g1SService.isLoggedIn(session)).thenReturn(true);
        mockMvc.perform(get("/subprojects").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("subprojects"));
    }

    //Testing if you can access create subproject webpage
    @Test
    void createSubproject() throws Exception {
        when(g1SService.isLoggedIn(session)).thenReturn(true);
        mockMvc.perform(get("/create/subproject/1").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("createSubproject"));

    }

    //Testing if you can add a new sub project
    @Test
    void addSubproject() throws Exception {
        when(g1SService.isLoggedIn(session)).thenReturn(true);
        mockMvc.perform(post("/create/subproject").session(session)
                .param("subprojectID", "1")
                .param("subprojectName","test")
                .param("subprojectStartDate","2025-05-10")
                .param("subprojectEndDate","2025-05-10")
                .param("projectID", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/" + 2));

    }

    //Testing if you can access the login page when no session available
    @Test
    void loginPage() throws Exception {

        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));

    }

    //Testing if you can login with a created user
    @Test
    void checkLoginSuccessfully() throws Exception {

        when(g1SService.findByUsername(employee.getEmployeeUsername())).thenReturn(employee);
        when(g1SService.verifyPassword(employee.getEmployeePassword(), employee.getEmployeePassword())).thenReturn(true);
        mockMvc.perform((post("/login"))
                        .param("checkUsername",employee.getEmployeeUsername())
                        .param("checkUserpassword",employee.getEmployeePassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"));
    }

    //Testing if you cannot login with invalid credentials
    @Test
    void checkLoginInvalid() throws Exception {
        when(g1SService.login(null, null)).thenReturn(null);

        mockMvc.perform(post("/login")
                        .param("checkUsername", "wrong")
                        .param("checkUserpassword", "wrong"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("wrongLogin"));
    }

    //Testing if you can log out
    @Test
    void logout() throws Exception {

        mockMvc.perform(get("/logout").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    //Testing if you can access projects site while logged in
    @Test
    void myprojects() throws Exception {
        when(g1SService.isLoggedIn(session)).thenReturn(true);
        mockMvc.perform(get("/projects").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("myProjects"));
    }

    //Testing if you cannot access home while not logged in
    @Test
    void homeIfNotLoggedIn() throws Exception {

        mockMvc.perform(get("/projects"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    //Testing if you can delete a sub project
    @Test
    void deleteSubproject() throws Exception {
        when(g1SService.getSubProjectById(project.getProjectId())).thenReturn(subProject);
        when(g1SService.isLoggedIn(session)).thenReturn(true);
        mockMvc.perform(post("/subproject/delete/" + subProject.getSubprojectID()).session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/" + subProject.getProjectID()));

    }

    //Testing if you can update a subproject
    @Test
    void updateSubproject () throws Exception {

        when(g1SService.isLoggedIn(session)).thenReturn(true);
        mockMvc.perform(post("/subprojects/update").session(session)
                .param("subprojectID", "1")
                .param("subprojectName", "testing")
                .param("subprojectStartDate", "2025-05-10")
                .param("subprojectEndDate", "2025-05-10")
                .param("projectID", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/" + 1));

    }

    //mangler nedenstående controller test

    @Test
    void editSubprojectView() throws Exception {}

    @Test
    void subProjectView() throws Exception {}

    @Test
    void testUrl() throws Exception {}

    @Test
    void adminPanel() throws Exception {
        when(g1SService.isLoggedIn(session)).thenReturn(true);
        mockMvc.perform(get("/adminpanel").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("adminPanel"));
    }

    @Test
    void adminPanelRoleIdNotAllowed() throws Exception{
        employee.setRoleId(1);
        when(g1SService.isLoggedIn(session)).thenReturn(true);
        mockMvc.perform(get("/adminpanel").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"));
    }

    @Test
    void adminPanelAddEmployee() throws Exception {
        when(g1SService.isLoggedIn(session)).thenReturn(true);
        mockMvc.perform(get("/admin/addEmployee").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("adminAddEmployee"));
    }
    @Test
    void adminPanelAddEmployeeIdNotAllowed() throws Exception {
        when(g1SService.isLoggedIn(session)).thenReturn(true);
        employee.setRoleId(1);
        mockMvc.perform(get("/admin/addEmployee").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"));
    }

    @Test
    void adminRegisterEmployee() throws Exception {
        when(g1SService.isLoggedIn(session)).thenReturn(true);
        when(g1SService.isUsernameFree("hannibal", 2)).thenReturn(true);
        mockMvc.perform(post("/admin/register").session(session)
                        .param("employeeId", "2")
                        .param("employeeName", "hannibal")
                        .param("employeeEmail", "hannibal@ussing.com")
                        .param("employeeUsername", "huw02")
                        .param("employeePassword", "1234")
                        .param("roleId", "3"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/addEmployee"));
    }

    @Test
    void adminDeleteEmployee () throws Exception {
        when(g1SService.isLoggedIn(session)).thenReturn(true);
        mockMvc.perform(post("/admin/delete/2").session(session)
                .param("employeeId", "2")
                .param("employeeName", "hannibal")
                .param("employeeEmail", "hannibal@ussing.com")
                .param("employeeUsername", "huw02")
                .param("employeePassword", "1234")
                .param("roleId", "3"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/adminpanel"));

    }

    @Test
    void deleteSubTask() throws Exception {}

    @Test
    void deleteTask() throws Exception {}

    @Test
    void editTask() throws Exception {}

    @Test
    void editSubTask() throws Exception {}

    //tester at man bliver redirected til "/home", hvis man ikke har rollen 2 eller 3
    @Test
    void adminUpdateEmployeeGetRoleIdNotAllowed () throws Exception {

        employee.setRoleId(1);
        when(g1SService.isLoggedIn(session)).thenReturn(true);
        mockMvc.perform(get("/admin/update/1").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"));
    }
    //tester man bliver sendt til html siden "adminUpdateEmployee", hvis man har rollen 2 eller 3
    @Test
    void adminUpdateEmployeeGet() throws  Exception{
        when(g1SService.isLoggedIn(session)).thenReturn(true);
        employee.setRoleId(3);
        when(g1SService.getEmployeeById(1)).thenReturn(employee);
        when(g1SService.getEmployeeByIdPlusSkills(1)).thenReturn(oldEmployee);
        mockMvc.perform(get("/admin/update/1").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("adminUpdateEmployee"));
    }

    @Test
    void adminUpdateEmployeePostUsernameNotFree () throws Exception {
        when(g1SService.isLoggedIn(session)).thenReturn(true);
        when(g1SService.isUsernameFree("hannibal", 2)).thenReturn(false);
        mockMvc.perform(post("/admin/update").session(session)
                        .param("employeeId", "2")
                        .param("employeeName", "hannibal")
                        .param("employeeEmail", "hannibal@ussing.com")
                        .param("employeeUsername", "huw02")
                        .param("employeePassword", "1234")
                        .param("roleId", "3"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/update/2"));
    }

    @Test
    void adminUpdateEmployeePostUsernameIsFree () throws Exception {
        when(g1SService.isLoggedIn(session)).thenReturn(true);
        when(g1SService.isUsernameFree("hannibal", 2)).thenReturn(true);
        mockMvc.perform(post("/admin/update").session(session)
                        .param("employeeId", "2")
                        .param("employeeName", "hannibal")
                        .param("employeeEmail", "hannibal@ussing.com")
                        .param("employeeUsername", "huw02")
                        .param("employeePassword", "1234")
                        .param("roleId", "3"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/update/" + 2));
    }
    @Test
    void getEmployees() throws  Exception{
        when(g1SService.isLoggedIn(session)).thenReturn(true);
        mockMvc.perform(get("/project/1/assignees").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("employeesWithSkill"));
    }

    @Test
    void addEmployeeToProjectEmployeeIdIsZero() throws Exception{
        when(g1SService.isLoggedIn(session)).thenReturn(true);
        mockMvc.perform(post("/project/1/add/0").session(session)
                        .param("employeeId", "0")
                        .param("employeeName", "hannibal")
                        .param("employeeEmail", "hannibal@ussing.com")
                        .param("employeeUsername", "huw02")
                        .param("employeePassword", "1234")
                        .param("roleId", "3"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/1"));
    }

    @Test
    void addEmployeeToProject() throws Exception{
        when(g1SService.isLoggedIn(session)).thenReturn(true);
        mockMvc.perform(post("/project/1/add/1").session(session)
                        .param("employeeId", "1")
                        .param("employeeName", "hannibal")
                        .param("employeeEmail", "hannibal@ussing.com")
                        .param("employeeUsername", "huw02")
                        .param("employeePassword", "1234")
                        .param("roleId", "3"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/1/assignees"));
    }

    @Test
    void seeProfile() throws Exception{
        List<Skill> skillList = new ArrayList<>();
        skillList.add(skill);
        when(g1SService.getEmployeeById(1)).thenReturn(employee);
        when(g1SService.isLoggedIn(session)).thenReturn(true);
        when(g1SService.getSkillsForEmployee(1)).thenReturn(skillList);
        mockMvc.perform(get("/profile/1").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"));
    }
    @Test
    void sortMyTasks() throws Exception{

        List<SubTask>subTaskList = new ArrayList<>();
        subTaskList.add(subtask);
        String a = "subtask_estimate";
        when(g1SService.isLoggedIn(session)).thenReturn(true);
        when(g1SService.getSortedSubtaskByEmployeeId(a, employee.getEmployeeId())).thenReturn(subTaskList);
        mockMvc.perform(get("/mySubTasks/sortBy").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("mySubTasks"));

    }






}

