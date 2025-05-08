package apiassignment.alphasolutions;


import apiassignment.alphasolutions.model.Project;
import apiassignment.alphasolutions.model.SubProject;
import apiassignment.alphasolutions.repository.G1SRepository;
import apiassignment.alphasolutions.service.G1SService;
import com.mysql.cj.xdevapi.Table;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:h2init.sql"}
)
@Transactional
@Rollback(true)
public class alphaSolutionsIntegrationsTest {


    @Autowired
    private G1SRepository g1SRepository;

    @Autowired
    private G1SService g1SService;

    @BeforeEach
    void setup() {}

    @AfterEach
    void teardown() {}

    @Test
    void testgetAllSubprojects() throws SQLException {
        List<SubProject> allSubprojects = g1SService.getAllSubProjects();

        assertNotNull(allSubprojects);
        assertTrue(!allSubprojects.isEmpty());

    }

    @Test
    void testGetAllProjectsForEmployee() {
        int employeeId = 1;
        List<Project> projects = g1SRepository.getAllProjects(employeeId);

        // Kontrollerer at listen ikke er null eller tom
        assertNotNull(projects);
        assertFalse(projects.isEmpty());

        List<String> expectedProjectNames = List.of(
                "Customer Portal Redesign",
                "Security Compliance Project",
                "Internal HR System"
        );

        List<String> actualProjectNames = new ArrayList<>();
        for(Project p : projects) {
            actualProjectNames.add(p.getProjectName());
        }

        assertTrue(actualProjectNames.containsAll(expectedProjectNames));
    }

    @Test
    void testGetProjectById() {
        int projectId = 1;
        Project project = g1SRepository.getProjectById(projectId);

        // Kontrollerer at listen ikke er null
        assertNotNull(project);

        String expectedName = "Customer Portal Redesign";
        String actualName = project.getProjectName();

        assertEquals(expectedName, actualName);
    }

    @Test
    void testCreateProject() {
        // Der oprettes et nyt projekt
        Project newProject = new Project();
        newProject.setProjectName("Test project");
        newProject.setProjectStartDate(Date.valueOf("2025-05-08"));
        newProject.setProjectEndDate(Date.valueOf("2025-05-09"));
        newProject.setEmployeeId(1);
        newProject.setProjectDescription("Test project description");

        g1SRepository.createProject(newProject);

        // Tester om projektet har fået tildelt et projektId
        assertTrue(newProject.getProjectId() > 0);

        // Henter projektet fra databasen og tester om værdierne er de samme
        Project dbProject = g1SRepository.getProjectById(newProject.getProjectId());

        assertNotNull(dbProject);
        assertEquals(newProject.getProjectName(),(dbProject.getProjectName()));
        assertEquals(newProject.getProjectStartDate(),dbProject.getProjectStartDate());
        assertEquals(newProject.getProjectEndDate(),dbProject.getProjectEndDate());
        assertEquals(newProject.getEmployeeId(),dbProject.getEmployeeId());
        assertEquals(newProject.getProjectDescription(),dbProject.getProjectDescription());
    }

    @Test
    void testDeleteProject() {
        Project project = new Project();
        project.setProjectName("Project to Delete");
        project.setProjectStartDate(Date.valueOf("2025-05-08"));
        project.setProjectEndDate(Date.valueOf("2025-05-09"));
        project.setEmployeeId(1);
        project.setProjectDescription("project delete test description");

        g1SRepository.createProject(project);
        int projectId = project.getProjectId();

        // Der undersøges om projektet findes i databasen
        Project fetched = g1SRepository.getProjectById(projectId);
        assertNotNull(fetched);

        g1SRepository.deleteProject(projectId);

        // Tester at projektet er fjernet fra databasen
        Project deletedProject = g1SRepository.getProjectById(projectId);
        assertNull(deletedProject);
    }

}
