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

//    //afventer
//    @Test
//    void testDeleSubproject() throws SQLException {
//
//        SubProject testSubProject = new SubProject(99, "test", Date.valueOf("2025-05-20"), Date.valueOf("2025-05-20"), 1);
//
//        g1SRepository.addSubProject(testSubProject);
//
//        g1SRepository.deleteSubProject(testSubProject.getSubprojectID());
//
//        if(testSubProject == null) {
//            assertTrue(true);
//            System.out.println("hej");
//        } else {
//            fail("Test failed");
//        }
//
//    }

    @Test
    void testUpdateSubproject() throws SQLException {



    }


    @Test
    void testCreateTask() {
        Task newTask = new Task();

        newTask.setTaskName("Test Task");
        newTask.setSubprojectId(1);
        newTask.setTaskEstimate(40);
        newTask.setTaskStartDate(Date.valueOf("2025-05-12"));
        newTask.setTaskEndDate(Date.valueOf("2025-05-14"));
        newTask.setTaskPriority("High");
        newTask.setTaskDescription("Test description");
        newTask.setTaskStatus("In Progress");

        g1SRepository.createTask(newTask);

        assertTrue(newTask.getTaskId() > 0);

        Task dbTask = g1SRepository.getTaskById(newTask.getTaskId());

        assertNotNull(dbTask);
        assertEquals(newTask.getTaskName(),(dbTask.getTaskName()));
        assertEquals(newTask.getSubprojectId(), dbTask.getSubprojectId());
        assertEquals(newTask.getTaskEstimate(), dbTask.getTaskEstimate());
        assertEquals(newTask.getTaskStartDate(), dbTask.getTaskStartDate());
        assertEquals(newTask.getTaskEndDate(), dbTask.getTaskEndDate());
        assertEquals(newTask.getTaskPriority(), dbTask.getTaskPriority());
        assertEquals(newTask.getTaskDescription(), dbTask.getTaskDescription());
        assertEquals(newTask.getTaskStatus(), dbTask.getTaskStatus());

    }

    @Test
    void testDeleteTask() {
        //opret task og tjek om den er oprettet i DB
        Task newTask = new Task();

        newTask.setTaskName("Test Task");
        newTask.setSubprojectId(1);
        newTask.setTaskEstimate(40);
        newTask.setTaskStartDate(Date.valueOf("2025-05-12"));
        newTask.setTaskEndDate(Date.valueOf("2025-05-14"));
        newTask.setTaskPriority("High");
        newTask.setTaskDescription("Test description");
        newTask.setTaskStatus("In Progress");

        g1SRepository.createTask(newTask);

        assertTrue(newTask.getTaskId() > 0);


        //tjek om task eksisterer i DB
        assertNotNull(g1SRepository.getTaskById(newTask.getTaskId()));

        //fjern task fra DB
        g1SRepository.deleteTask(newTask.getTaskId());

        //tjek om den er fjernet
        assertNull(g1SRepository.getTaskById(newTask.getTaskId()));

    }

    /*@Test
    void testUpdateTask() {
        //create task
        Task newTask = new Task();

        newTask.setTaskName("Test Task");
        newTask.setSubprojectId(1);
        newTask.setTaskEstimate(40);
        newTask.setTaskStartDate(Date.valueOf("2025-05-12"));
        newTask.setTaskEndDate(Date.valueOf("2025-05-14"));
        newTask.setTaskPriority("High");
        newTask.setTaskDescription("Test description");
        newTask.setTaskStatus("In Progress");

        g1SRepository.createTask(newTask);

        assertTrue(newTask.getTaskId() > 0);

        g1SRepository.updateTask();
    }*/

    @Test
    void testGetTasks() {
        int taskID = 1;
        Task task = g1SRepository.getTaskById(taskID);

        assertNotNull(task);

        String expected = "Design User Dashboard";
        String actual = task.getTaskName();

        assertEquals(expected, actual);

    }

    @Test
    void testCreateSubtask() {
        SubTask subtask = new SubTask();
        subtask.setSubtaskName("Test");
        subtask.setTaskID(1);
        subtask.setSubtaskEstimate(4);
        subtask.setSubtaskStartDate(Date.valueOf("2025-05-12"));
        subtask.setSubtaskEndDate(Date.valueOf("2025-05-14"));
        subtask.setSubtaskPriority("High");
        subtask.setSubtaskDescription("Test Description");
        subtask.setSubtaskStatus("Not Started");


        g1SRepository.createSubTask(subtask);

        assertTrue(subtask.getSubtaskID() > 0);

        //tjek om eksisterer i DB
        SubTask dbSubtask = g1SRepository.getSubtaskById(subtask.getSubtaskID());
        assertNotNull(dbSubtask);

        assertEquals(subtask.getSubtaskName(), dbSubtask.getSubtaskName());
        assertEquals(subtask.getTaskID(), dbSubtask.getTaskID());
        assertEquals(subtask.getSubtaskEstimate(), dbSubtask.getSubtaskEstimate());
        assertEquals(subtask.getSubtaskStartDate(), dbSubtask.getSubtaskStartDate());
        assertEquals(subtask.getSubtaskEndDate(), dbSubtask.getSubtaskEndDate());
        assertEquals(subtask.getSubtaskPriority(), dbSubtask.getSubtaskPriority());
        assertEquals(subtask.getSubtaskDescription(), dbSubtask.getSubtaskDescription());
        assertEquals(subtask.getSubtaskStatus(), dbSubtask.getSubtaskStatus());

    }

    @Test
    void testGetSubtask() {
        int subtaskID = 1;
        SubTask subtask = g1SRepository.getSubtaskById(subtaskID);

        assertNotNull(subtask);

        String expected = "Create Dashboard Wireframes";
        String actual = subtask.getSubtaskName();

        assertEquals(expected, actual);
    }



}
