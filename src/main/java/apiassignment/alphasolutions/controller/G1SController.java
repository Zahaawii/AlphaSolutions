package apiassignment.alphasolutions.controller;

import apiassignment.alphasolutions.model.Task;
import apiassignment.alphasolutions.service.G1SService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class G1SController {

    @Autowired
    private G1SService g1sService;


    @GetMapping("/home")
    public String home () {
        return "home";
    }


    @GetMapping("/subproject/{id}")
    public String subProjectView (@PathVariable("id") int subprojectId, Model model) {
        List<Task> tasks = g1sService.getTasksBySubprojectId(subprojectId);
        model.addAttribute("tasks", tasks);



        return "subprojectview";
    }

    @GetMapping("/test/{id}")
    public String testUrl (@PathVariable("id") int subprojectId, Model model) {
        model.addAttribute("tasks", g1sService.getTasksBySubprojectId(subprojectId));

        return "test";
    }

}
