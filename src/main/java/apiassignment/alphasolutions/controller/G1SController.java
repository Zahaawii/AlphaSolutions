package apiassignment.alphasolutions.controller;

import apiassignment.alphasolutions.model.Project;
import apiassignment.alphasolutions.service.G1SService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class G1SController {

    private final G1SService g1SService;

    public G1SController(G1SService g1SService) {
        this.g1SService = g1SService;
    }

    @GetMapping("/testlogin")
    public String testLogin(HttpSession session) {
        session.setAttribute("employeeID", 1);
        return "redirect:/projects";
    }

    @GetMapping("/projects")
    public String getMyProjects(Model model, HttpSession session) {
        Integer employeeID = (Integer) session.getAttribute("employeeID");
        if (employeeID == null) {
            return "redirect:/login";
        }

        model.addAttribute("projects", g1SService.getAllProjects(employeeID));
        return "projects";
    }

    @GetMapping("/projects/new")
    public String newProject(Model model) {
        model.addAttribute("project", new Project());
        model.addAttribute("employees", g1SService.getAllEmployees()); // til multiselect
        return "newProject";
    }

    @PostMapping("/projects")
    public String createProject(@ModelAttribute Project project,
                                @RequestParam(value = "assignees", required = false) List<Integer> assigneeIds,
                                HttpSession session) {
        Integer loggedInId = (Integer) session.getAttribute("employeeID");
        if (loggedInId == null) {
            return "redirect:/login";
        }
        // Her sættes projektlederen som ejer af projektet.
        project.setEmployeeId(loggedInId);
        if (assigneeIds == null) {
            assigneeIds = List.of();
        }

        g1SService.createProjectWithAssignees(project, assigneeIds);
        return "redirect:/projects";
    }

    @GetMapping("/projects/edit/{id}")
    public String editProject(@PathVariable int id, Model model, HttpSession session) {
        Integer loggedInId = (Integer) session.getAttribute("employeeID");
        Project project = g1SService.getProjectById(id);

        if (project == null || project.getEmployeeId() != loggedInId) {
            return "redirect:/access-denied"; // adgangscheck
        }

        model.addAttribute("project", project);
        model.addAttribute("employees", g1SService.getAllEmployees());
        model.addAttribute("assignees", g1SService.getProjectAssignees(id));
        return "editProject";
    }

    @PostMapping("/projects/update")
    public String updateProject(@ModelAttribute Project project,
                                @RequestParam(value = "assignees", required = false) List<Integer> assigneeIds,
                                HttpSession session) {
        Integer loggedInId = (Integer) session.getAttribute("employeeID");

        if (project.getEmployeeId() != loggedInId) {
            return "redirect:/home";
        }

        if (assigneeIds == null) {
            assigneeIds = List.of();
        }

        g1SService.updateProjectWithAssignees(project, assigneeIds);
        return "redirect:/projects";
    }

    @GetMapping("/projects/delete/{id}")
    public String deleteProject(@PathVariable int id, HttpSession session) {
        Integer loggedInId = (Integer) session.getAttribute("employeeID");
        Project project = g1SService.getProjectById(id);

        if (project != null && project.getEmployeeId() == loggedInId) {
            g1SService.deleteProject(id);
        }

        return "redirect:/projects";
    }
}
