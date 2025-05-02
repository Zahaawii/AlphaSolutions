package apiassignment.alphasolutions.controller;

import apiassignment.alphasolutions.model.SubProject;
import apiassignment.alphasolutions.service.G1SService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class G1SController {

    private final G1SService g1SService;


    public G1SController(G1SService g1SService) {
        this.g1SService = g1SService;
    }

    @GetMapping("")
    public String homepage() {
        return "homepage";
    }

    @GetMapping("/subprojects")
    public String subProjects(Model model) {
        List<SubProject> getAllSubProjects = g1SService.getAllSubProjects();
        model.addAttribute("AllSubProjects", getAllSubProjects);
        System.out.println(getAllSubProjects);
        return "subprojects";
    }
}
