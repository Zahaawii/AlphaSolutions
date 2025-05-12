package apiassignment.alphasolutions;


import apiassignment.alphasolutions.model.Project;
import apiassignment.alphasolutions.model.SubProject;
import apiassignment.alphasolutions.model.SubTask;
import apiassignment.alphasolutions.model.Task;
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
import java.util.Optional;

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

    //Testing if you get all subprojects
    @Test
    void testGetAllSubProjects() throws SQLException {
        List<SubProject> getAll = g1SService.getAllSubProjects();

        if(!getAll.isEmpty()) {
            System.out.println(getAll.size());
            assertTrue(true);
        } else {
            assertFalse(false);
        }
    }

    @Test
    void testAddSubProjectAndGetSubprojectByID() throws SQLException {

        SubProject testSubProject = new SubProject(99, "test", Date.valueOf("2025-05-20"), Date.valueOf("2025-05-20"), 1);
        g1SService.addSubproject(testSubProject);

       assertTrue(testSubProject.getProjectID() > 0);

        SubProject test = g1SRepository.getSubProjectById(testSubProject.getSubprojectID());

        assertNotNull(test);
        assertEquals(testSubProject.getSubprojectName(),(test.getSubprojectName()));
        assertEquals(testSubProject.getSubprojectStartDate(),test.getSubprojectStartDate());
        assertEquals(testSubProject.getSubprojectEndDate(),test.getSubprojectEndDate());

    }

    //afventer
    @Test
    void testDeleSubproject() throws SQLException {

    }

    @Test
    void testUpdateSubProject() {
        SubProject subProject = new SubProject(0, "Test name", Date.valueOf("2025-05-20"), Date.valueOf("2025-05-25"), 1);
        g1SRepository.addSubProject(subProject);
        int id = subProject.getSubprojectID();

        SubProject createdSubProject = g1SRepository.getSubProjectById(id);
        assertNotNull(createdSubProject);
        assertEquals("Test name", createdSubProject.getSubprojectName());

        createdSubProject.setSubprojectName("Test1 name");
        createdSubProject.setSubprojectStartDate(Date.valueOf("2025-06-01"));
        createdSubProject.setSubprojectEndDate(Date.valueOf("2025-06-15"));
        g1SRepository.updateSubproject(createdSubProject);

        SubProject updatedSubProject = g1SRepository.getSubProjectById(id);
        assertNotNull(updatedSubProject);

        assertEquals("Test1 name", updatedSubProject.getSubprojectName());
        assertEquals(Date.valueOf("2025-06-01"), updatedSubProject.getSubprojectStartDate());
        assertEquals(Date.valueOf("2025-06-15"), updatedSubProject.getSubprojectEndDate());
    }

    @Test
    void testGetSumOfTaskAndSubTask() throws SQLException {
        SubProject testSub = new SubProject
                        (99, "test",
                        Date.valueOf("2025-05-12"),
                        Date.valueOf("2025-05-12"),
                        1);
        g1SRepository.addSubProject(testSub);
        int subprojectId = testSub.getSubprojectID();

        Task taskWithEstimate = new Task(1, "test task", 99, 10,
                Date.valueOf("2025-05-12"),
                Date.valueOf("2025-05-12"),
                "high", "test", "in progress", null
                );
        g1SRepository.createTask(taskWithEstimate);

        Task taskWithOutEstimate = new Task(1, "test task", 99, 0,
                Date.valueOf("2025-05-12"),
                Date.valueOf("2025-05-12"),
                "high", "test", "in progress", null
        );
        g1SRepository.createTask(taskWithOutEstimate);

        SubTask subTaskWithEstimate = new SubTask(1, "test task", 1, 10,
                Date.valueOf("2025-05-12"),
                Date.valueOf("2025-05-12"),
                "high", "test", "in progress", null
        );
        g1SRepository.createSubTask(subTaskWithEstimate);

        SubTask subTaskWithoutEstimate = new SubTask(1, "test task", 1, 0,
                Date.valueOf("2025-05-12"),
                Date.valueOf("2025-05-12"),
                "high", "test", "in progress", null
        );
        g1SRepository.createSubTask(subTaskWithoutEstimate);



       Integer result = g1SService.getSumOfTaskAndSubTask(subprojectId);
        assertNotNull(result);
        assertEquals(10, result);

    }

    @Test
    void testGetTotalSumOfProject() throws SQLException {
        Project testProject = new Project(99, "test", 1, Date.valueOf("2025-05-12"), Date.valueOf("2025-05-12"),"testDB", "test");
        g1SRepository.createProject(testProject);

        SubProject testSub = new SubProject
                (99, "test",
                        Date.valueOf("2025-05-12"),
                        Date.valueOf("2025-05-12"),
                        testProject.getProjectId());
        g1SRepository.addSubProject(testSub);
        int subprojectId = testSub.getSubprojectID();

        Task taskWithEstimate = new Task(1, "test task", subprojectId, 10,
                Date.valueOf("2025-05-12"),
                Date.valueOf("2025-05-12"),
                "high", "test", "in progress", null
        );
        g1SRepository.createTask(taskWithEstimate);

        Task taskWithOutEstimate = new Task(2, "test task", subprojectId, 0,
                Date.valueOf("2025-05-12"),
                Date.valueOf("2025-05-12"),
                "high", "test", "in progress", null
        );
        g1SRepository.createTask(taskWithOutEstimate);

        SubTask subTaskWithEstimate = new SubTask(1, "test task", 1, 10,
                Date.valueOf("2025-05-12"),
                Date.valueOf("2025-05-12"),
                "high", "test", "in progress", null
        );
        g1SRepository.createSubTask(subTaskWithEstimate);

        SubTask subTaskWithoutEstimate = new SubTask(2, "test task", 2, 0,
                Date.valueOf("2025-05-12"),
                Date.valueOf("2025-05-12"),
                "high", "test", "in progress", null
        );
        g1SRepository.createSubTask(subTaskWithoutEstimate);


        Integer result = g1SService.getTotalSumOfProject(testSub.getProjectID());
        assertNotNull(result);
        assertEquals(10, result);
    }



}
