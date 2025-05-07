package apiassignment.alphasolutions;


import apiassignment.alphasolutions.controller.G1SController;
import apiassignment.alphasolutions.model.Employee;
import apiassignment.alphasolutions.model.Project;
import apiassignment.alphasolutions.model.SubProject;
import apiassignment.alphasolutions.service.G1SService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(G1SController.class)
public class controllerTest {

    //Adding test objects to testing page, so we can test our controllers with sesison
    Employee employee;
    MockHttpSession session;
    Project project;
    SubProject subProject;

    //Calling MockMvc method to test our controllers
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private G1SService g1SService;

    //Before each run the code will setup these variables to be able to test
    @BeforeEach
    void setup() {
        employee = new Employee(1, "test","test","test","test",null, 3);
        session = new MockHttpSession();
        session.setAttribute("employee", employee);
        project = new Project(1, "Project test", 1,null, null, "test", "igang");
        subProject = new SubProject(1, "subProject test", null,null,1);
    }

    @AfterEach
    void tearDown() {

    }

    //Testing to see if you land on our homepage
    @Test
    void homepage() throws Exception {
        mockMvc.perform(get(""))
                .andExpect(status().isOk())
                .andExpect(view().name("homepage"));
    }

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
        mockMvc.perform(get("/projects").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("myprojects"));
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
        mockMvc.perform(get("/projects/edit/" + project.getProjectId()).session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("editProject"));
    }

    //Testing if you can update a project
    @Test
    void updateProject() throws Exception {

        mockMvc.perform(post("/projects/update").
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

        mockMvc.perform(get("/projects/delete/1").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"));
    }

    //Testing if you can see subprojects by mocking a list of subprojects
    @Test
    void viewSubprojects () throws Exception {

        List<SubProject> testList = List.of(subProject);
        when(g1SService.getSubProjectByProjectId(1)).thenReturn(testList);
        mockMvc.perform(get("/project/1").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("myprojectSubproject"));

    }

    @Test
    void selectCollaborators() throws Exception {
        mockMvc.perform(get("/select-collaborators").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("selectCollaborators"));
    }

    //Testing if you can see all subprojects that has been mocked
    @Test
    void getAllSubProjects() throws Exception {
        List<SubProject> testList = List.of(subProject,
                new SubProject(5,"2", null, null,3));
        when(g1SService.getAllSubProjects()).thenReturn(testList);

        mockMvc.perform(get("/subprojects").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("subprojects"));
    }

    //Testing if you can access create subproject webpage
    @Test
    void createSubproject() throws Exception {

        mockMvc.perform(get("/create/subproject/1").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("createSubproject"));

    }

    //Testing if you can add a new sub project
    @Test
    void addSubproject() throws Exception {

        mockMvc.perform(post("/create/subproject").session(session)
                .param("subprojectID", "1")
                .param("subprojectName","test")
                .param("subprojectStartDate","2025-05-10")
                .param("subprojectEndDate","2025-05-10")
                .param("projectID", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));

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

        when(g1SService.login(employee.getEmployeeUsername(), employee.getEmployeePassword())).thenReturn(employee);
        mockMvc.perform((post("/login"))
                        .param("checkUsername",employee.getEmployeeUsername())
                        .param("checkUserpassword",employee.getEmployeePassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
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

    //Testing if you can access home site while logged in
    @Test
    void home() throws Exception {

        mockMvc.perform(get("/home").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    //Testing if you cannot access home while not logged in
    @Test
    void homeIfNotLoggedIn() throws Exception {

        mockMvc.perform(get("/home"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    //Testing if you can delete a sub project
    @Test
    void deleteSubproject() throws Exception {

        mockMvc.perform(post("/subproject/delete/1").session(session)
                .param("subprojectID","1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"));

    }

    //Testing if you can update a subproject
    @Test
    void updateSubproject () throws Exception {


        mockMvc.perform(post("/subprojects/update").session(session)
                .param("subprojectID", "1")
                .param("subprojectName", "testing")
                .param("subprojectStartDate", "2025-05-10")
                .param("subprojectEndDate", "2025-05-10")
                .param("projectID", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"));

    }

    //mangler nedenstående controller test

    @Test
    void editSubprojectView() throws Exception {}

    @Test
    void subProjectView() throws Exception {}

    @Test
    void testUrl() throws Exception {}

    @Test
    void adminPanel() throws Exception {}

    @Test
    void adminPanelAddEmployee() throws Exception {}

    @Test
    void adminRegisterEmployee() throws Exception {}

    @Test
    void adminDeleteEmployee () throws Exception {}

    @Test
    void deleteSubTask() throws Exception {}

    @Test
    void deleteTask() throws Exception {}

    @Test
    void editTask() throws Exception {}

    @Test
    void editSubTask() throws Exception {}

    @Test
    void adminUpdateEmployeeGet () throws Exception {}

    @Test
    void adminUpdateEmployeePost () throws Exception {}



}

