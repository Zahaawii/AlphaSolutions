package apiassignment.alphasolutions.controller;

import apiassignment.alphasolutions.service.G1SService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class G1SController {

    @Autowired
    private G1SService g1sService;


    @GetMapping("/home")
    public String home () {
        return "home";
    }

}
