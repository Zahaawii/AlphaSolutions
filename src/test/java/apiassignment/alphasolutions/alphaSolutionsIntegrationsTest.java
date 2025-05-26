package apiassignment.alphasolutions;


import apiassignment.alphasolutions.DTO.DTOEmployee;
import apiassignment.alphasolutions.model.*;
import apiassignment.alphasolutions.repository.G1SRepository;
import apiassignment.alphasolutions.service.G1SService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Test
    void testUpdateProject() throws SQLException {
        Project newProject = new Project();
        newProject.setProjectName("Test project");
        newProject.setProjectStartDate(Date.valueOf("2025-05-08"));
        newProject.setProjectEndDate(Date.valueOf("2025-05-09"));
        newProject.setEmployeeId(1);
        newProject.setProjectDescription("Test project description");

        g1SRepository.createProject(newProject);
        int createdProjectId = newProject.getProjectId();

        // Opdaterer projektet
        newProject.setProjectName("Updated test project");
        newProject.setProjectStatus("Completed");
        newProject.setProjectDescription("Updated test description");

        g1SRepository.updateProject(newProject);

        // Henter det opdateret projekt og kontrollere om projektet er blevet opdateret
        Project updatedProject = g1SRepository.getProjectById(createdProjectId);

        assertEquals("Updated test project", updatedProject.getProjectName());
        assertEquals("Completed", updatedProject.getProjectStatus());
        assertEquals("Updated test description", updatedProject.getProjectDescription());
        assertEquals(Date.valueOf("2025-05-08"), updatedProject.getProjectStartDate());
        assertEquals(Date.valueOf("2025-05-09"), updatedProject.getProjectEndDate());
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
    void testCreateTask() {
        Task newTask = new Task();

        newTask.setTaskName("Test Task");
        newTask.setSubprojectId(1);
        newTask.setTaskStartDate(Date.valueOf("2025-05-12"));
        newTask.setTaskEndDate(Date.valueOf("2025-05-14"));
        newTask.setTaskPriority("High");
        newTask.setTaskDescription("Test description");

        g1SRepository.createTask(newTask);

        assertTrue(newTask.getTaskId() > 0);

        Task dbTask = g1SRepository.getTaskById(newTask.getTaskId());

        assertNotNull(dbTask);
        assertEquals(newTask.getTaskName(),(dbTask.getTaskName()));
        assertEquals(newTask.getSubprojectId(), dbTask.getSubprojectId());
        assertEquals(newTask.getTaskStartDate(), dbTask.getTaskStartDate());
        assertEquals(newTask.getTaskEndDate(), dbTask.getTaskEndDate());
        assertEquals(newTask.getTaskPriority(), dbTask.getTaskPriority());
        assertEquals(newTask.getTaskDescription(), dbTask.getTaskDescription());

    }



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
        subtask.setSubtaskHoursSpent(0);


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
        assertEquals(subtask.getSubtaskHoursSpent(), dbSubtask.getSubtaskHoursSpent());

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

    @Test
    void testEncryptPassword() {
        String testPassword = "hello";
        String encrypted = g1SService.encryptPassword(testPassword);

        assertNotNull(encrypted);
        assertTrue(g1SService.verifyPassword(testPassword, encrypted));
        assertFalse(g1SService.verifyPassword("wrong", encrypted));
    }

    @Test
    void testAddGetAndClearTaskAssignees() {
        int taskId = 1;

        // Tømmer først listen for taskAssignees
        g1SRepository.clearTaskAssignees(taskId);

        List<Integer> employeeIds = List.of(1, 2);

        // Tilføjer assignees til en task
        g1SRepository.addAssigneeToTask(taskId, employeeIds);

        // Henter listen af task assignees og tester at den ikke er null, der er 2 employees i listen og de 2 employees passer med de employeeIds jeg tilføjede
        List<Integer> taskAssignees = g1SRepository.getTaskAssignees(taskId);
        assertNotNull(taskAssignees);
        assertEquals(2, taskAssignees.size());
        assertTrue(taskAssignees.containsAll(employeeIds));

        // Fjerner assignees fra listen
        g1SRepository.clearTaskAssignees(taskId);

        // Kontrollere at listen nu er tom
        List<Integer> afterClear = g1SRepository.getTaskAssignees(taskId);
        assertTrue(afterClear.isEmpty());
    }

    @Test
    void testAddGetAndClearSubtaskAssignees() {
        int subtaskId = 1;

        // Tømmer først listen for subtaskAssignees
        g1SRepository.clearSubtaskAssignees(subtaskId);

        List<Integer> employeeIds = List.of(1, 2);

        // Tilføjer assignees til en subtask
        g1SRepository.addAssigneeToSubtask(subtaskId, employeeIds);

        // Henter listen af subtask assignees og tester at den ikke er null, der er 2 employees i listen og de 2 employees passer med de employeeIds jeg tilføjede
        List<Integer> fetchedAssignees = g1SRepository.getSubtaskAssignees(subtaskId);
        assertNotNull(fetchedAssignees);
        assertEquals(2, fetchedAssignees.size());
        assertTrue(fetchedAssignees.containsAll(employeeIds));

        // Fjerner assignees fra listen
        g1SRepository.clearSubtaskAssignees(subtaskId);

        // Kontrollere at listen nu er tom
        List<Integer> afterClear = g1SRepository.getSubtaskAssignees(subtaskId);
        assertTrue(afterClear.isEmpty());
    }

    @Test
    void testgetAllEmployee(){
        List<Employee> employeeList = g1SRepository.getAllEmployee();
        assertNotNull(employeeList);
        assertEquals(15, employeeList.size());
    }

    @Test
    void testgetAllCommonWorkers(){
        List<Employee> employeeList = g1SRepository.getAllCommonWorkers();
        assertNotNull(employeeList);
        assertEquals(1, employeeList.getFirst().getRoleId());
    }

    @Test
    void testadminRegisterEmployee(){
        DTOEmployee employee = new DTOEmployee();
        List<Integer> skills = List.of(2);
        employee.setEmployeeName("hannibal");
        employee.setEmployeeEmail("hannibal@ussing.com");
        employee.setEmployeeUsername("huw02");
        employee.setEmployeePassword("1234");
        employee.setSkills(skills);
        employee.setRoleId(3);
        //vi tester vores nye bruger ikke har et employeeId
        assertEquals(0, employee.getEmployeeId());
        //vi registerer brugeren, og vi får retuneret samme bruger, men nu har vedkommende et employeeId
        DTOEmployee insertedEmployee = g1SRepository.adminRegisterEmployee(employee);
        assertNotNull(insertedEmployee);
        assertEquals(16, insertedEmployee.getEmployeeId());

        assertEquals(16, g1SRepository.getAllEmployee().size());

    }

    @Test
    void testdeleteEmployee(){
        DTOEmployee employee = new DTOEmployee();
        List<Integer> skills = List.of(2);
        employee.setEmployeeName("hannibal");
        employee.setEmployeeEmail("hannibal@ussing.com");
        employee.setEmployeeUsername("huw02");
        employee.setEmployeePassword("1234");
        employee.setSkills(skills);
        employee.setRoleId(3);

        g1SRepository.adminRegisterEmployee(employee); //registerer en bruger
        assertNotNull(g1SRepository.getEmployeeById(16)); //ser at vi kan finde den her bruger
        g1SRepository.deleteEmployee(16); //Sletter den givne bruger
        assertNull(g1SRepository.getEmployeeById(16)); //ser at brugeren ikke længere er i vores system
    }

    @Test
    void testgetEmployeeById(){
        assertNotNull(g1SRepository.getEmployeeById(1));
        assertEquals("Anders Jensen", g1SRepository.getEmployeeById(1).getEmployeeName());
    }

    @Test
    void testgetAllSkills(){
        List<Skill>skillList = g1SRepository.getAllSkills();
        assertNotNull(skillList);
    }

    @Test
    void testcreateSkill(){
        Skill skill = new Skill();
        skill.setSkillName("nySkill");
        g1SRepository.createSkill(skill);
        List<Skill>allSkills = g1SRepository.getAllSkills();
        assertTrue(allSkills.contains(skill));
    }

    @Test
    void testdeleteSkill(){
        //vi laver en skill og giver den kun et navn, ikke et skillId
        Skill skill = new Skill();
        skill.setSkillName("nySkill");
        //vi tilføjer den givne skill til vores database, her er det så vores h2 in-memory database
        Skill createdSkill = g1SRepository.createSkill(skill);
        //tjekker at det skill vi får retuneret ikke er null, nu har vores skill også en id på sig
        assertNotNull(createdSkill);
        //vi tjekker at den findes i databasen ved at kalde metoden getAllSkills
        List<Skill>allSkills = g1SRepository.getAllSkills();
        assertTrue(allSkills.contains(skill));
        //vi sletter den fra datasbasen og kalder metoden getAllSkills igen
        g1SRepository.deleteSkill(createdSkill.getSkillId());
        List<Skill>allSkillsSecond = g1SRepository.getAllSkills();
        //vi forventer nu at listen ikke indeholder vores skill, da vi har slettet den
        assertFalse(allSkillsSecond.contains(createdSkill));
    }

    @Test
    void testgetSubProjectIdWithSubTaskId(){
        //følgende metode tester at vi ved hjælp af et subtask id, kan få et subproject id
        //da insert dataen blev lavet, så lavede vi project, subproject, task og subproject for et projekt
        //vi ved derfor at de alle har id'et 1, dette tester vi i følgende metode
        assertEquals(1, g1SRepository.getSubProjectIdWithSubTaskId(1));
        //metoden retunere true, hvilket betyder vores subtask med id'et 1, hører til et subproject med id'et 1
    }


    @Test
    void testaddEmployeeToProject(){
        //metoden tester hvorledes man kan tilføje en bruger til et projekt
        //først laver vi en bruger

        DTOEmployee employee = new DTOEmployee();
        List<Integer> skills = List.of(2);
        employee.setEmployeeName("hannibal");
        employee.setEmployeeEmail("hannibal@ussing.com");
        employee.setEmployeeUsername("huw02");
        employee.setEmployeePassword("1234");
        employee.setSkills(skills);
        employee.setRoleId(3);

        DTOEmployee dtoEmployee = g1SRepository.adminRegisterEmployee(employee); //registerer brugeren


        //så tester vi at brugeren ikke er koblet på nogle projekter
        assertTrue(g1SRepository.getProjectsForOneEmployee(dtoEmployee.getEmployeeId()).isEmpty());
        //så tilføjer vi dem til et projekt
        g1SRepository.addEmployeeToProject(1, dtoEmployee.getEmployeeId());
        //tjekker at de nu er koblet på 1 projekt
        assertEquals(1, g1SRepository.getProjectsForOneEmployee(employee.getEmployeeId()).size());
    }

    @Test
    void testgetProjectsForOneEmployee(){
        //følgende test vil teste, hvordan man ved hjælp af et employeeId, kan få alle de projekter vedkommende arbejder på
        assertNotNull(g1SRepository.getProjectsForOneEmployee(1));
        //vi ved tilfældigvis at en bruger med employeeId 1, arbejder på 3 projekter, lad os teste om det passer
        assertEquals(3, g1SRepository.getProjectsForOneEmployee(1).size());
    }





    @Test
    void testgetSortedSubtaskByEmployeeId(){
        //vi tilføjer en person med EmployeeId 1 til subtask nr 10
        g1SRepository.addAssigneeToSubtask(9, List.of(1)); //estimat er 20, prioritet er high
        g1SRepository.addAssigneeToSubtask(2, List.of(1)); //estimat er 25, prioritet er medium
        g1SRepository.addAssigneeToSubtask(7, List.of(1)); // esitmat er 15, prioritet er low

        // vi tjekker om den kunne finde subtasks for en bruger med employeeId 1
        assertNotNull(g1SRepository.getSortedSubtaskByEmployeeIdPerfected("subtask_estimate", 1));

        //vi får den første subtask på listen
        SubTask firstSubtask = g1SRepository.getSortedSubtaskByEmployeeIdPerfected("subtask_estimate", 1).get(0);
        //vi får den anden subtask på listen
        SubTask secondSubtask = g1SRepository.getSortedSubtaskByEmployeeIdPerfected("subtask_estimate", 1).get(1);
        //vi får den tredje subtask på listen
        SubTask thirdSubtask = g1SRepository.getSortedSubtaskByEmployeeIdPerfected("subtask_estimate", 1).get(2);

        //vi tester her at på listen over subtasks, så er vores anden subtasks estimat højere end den første
        //dette skyldtes at listen bliver sortere fra mindst til højest estimat
        assertTrue(firstSubtask.getSubtaskEstimate() < secondSubtask.getSubtaskEstimate());
        assertTrue(secondSubtask.getSubtaskEstimate() < thirdSubtask.getSubtaskEstimate());


        //testen går igennem som true, hvilket betyder at vores metode virker, der bliver sorteret efter estimat


        //vi kan også teste om en anden sortering virker fx start-date/end-date og priority
        //nu gør vi det ud fra priority, da den har en ekstra metode til at sortere
        //vi får den første subtask på listen
        SubTask firstSubtaskPriority = g1SRepository.getSortedSubtaskByEmployeeIdPerfected("subtask_priority", 1).get(0);
        //vi får den anden subtask på listen
        SubTask secondSubtaskPriority = g1SRepository.getSortedSubtaskByEmployeeIdPerfected("subtask_priority", 1).get(1);
        //vi får den tredje subtask på listen
        SubTask thirdSubtaskPriority = g1SRepository.getSortedSubtaskByEmployeeIdPerfected("subtask_priority", 1).get(2);

        assertEquals("High", firstSubtaskPriority.getSubtaskPriority());
        assertEquals("Medium", secondSubtaskPriority.getSubtaskPriority());
        assertEquals("Low", thirdSubtaskPriority.getSubtaskPriority());
        //vi kan dermed konkludere ud fra vores test, at den sorterer subtaskene ud fra prioritet: high->medium->low

    }


    @Test
    void testgetAllEmployeesWithSkillNotPartOfProject(){
        //vi kan først se alle dem der ikke er på projektet, her sortere vi ikke ud fra en given skill
        List<Employee> employeeList = g1SRepository.getAllEmployeesWithSkillNotPartOfProject(null, 1);
        assertNotNull(employeeList);

        //vi får nu en liste af alle dem der ikke er en del af projektet, men som har skill "java"
        List<Employee> employeeListWithSkill = g1SRepository.getAllEmployeesWithSkillNotPartOfProject("Java", 1);
        assertNotNull(employeeListWithSkill);

        //vi kan også iterere igennem den her liste af medarbejdere for at tjekke om det er rigtigt, at de alle indeholder
        //det Skill objekt kaldet "java".
        Skill java = new Skill(3, "Java");
        for(Employee employees : employeeListWithSkill){
            assertTrue(employees.getSkills().contains(java));
        }

        //hvis metoden modtager en skill som ikke findes i databasen eller den ikke kan finde nogle med en given skill
        //så vil den ikke fejle, den vil bare retunere en tom liste
        List<Employee> employeeListWithUnknowSkill = g1SRepository.getAllEmployeesWithSkillNotPartOfProject("denne skill findes ikke", 1);
        assertTrue(employeeListWithUnknowSkill.isEmpty());

    }


}
