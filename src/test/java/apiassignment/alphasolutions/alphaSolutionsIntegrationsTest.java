package apiassignment.alphasolutions;


import apiassignment.alphasolutions.model.SubProject;
import apiassignment.alphasolutions.repository.G1SRepository;
import apiassignment.alphasolutions.service.G1SService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
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
}
