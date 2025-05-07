package apiassignment.alphasolutions;


import apiassignment.alphasolutions.controller.G1SController;
import apiassignment.alphasolutions.model.Employee;
import apiassignment.alphasolutions.model.Project;
import apiassignment.alphasolutions.service.G1SService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(G1SController.class)
public class controllerTest {

    //Adding test objects to testing page, so we can test our controllers with sesison
    Employee employee;
    MockHttpSession session;

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

    //Testing if you can
    @Test
    void editProjectLoggedIn() throws Exception {

        Project project = new Project(1, "test", 1,null, null, "test", "igang");
        mockMvc.perform(get("/projects/edit/" + project.getProjectId()).session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("editProject"));
    }



}
