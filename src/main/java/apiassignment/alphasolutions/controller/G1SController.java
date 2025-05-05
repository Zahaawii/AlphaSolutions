package apiassignment.alphasolutions.controller;

import apiassignment.alphasolutions.model.Employee;
import apiassignment.alphasolutions.model.Role;
import apiassignment.alphasolutions.service.G1SService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class G1SController {
    private final G1SService g1SService;

    public G1SController (G1SService g1SService){
        this.g1SService = g1SService;
    }






    @GetMapping("/login")
    public String login(){
        return "login";
    }


    @PostMapping("/login")
    public String checkLogin(@RequestParam("checkUsername") String username, @RequestParam("checkUserpassword") String password,
                             HttpSession session, Model model){
        Employee employee = g1SService.login(username, password);
        System.out.println(employee);
        if(employee == null){
            model.addAttribute("wrongLogin", true);
            return "login";
        }
        session.setAttribute("employee", employee);
        session.setMaxInactiveInterval(3600);
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "login";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/adminPanel")
    public String adminPanel(HttpSession session, Model model){
        Employee checkEmployee = (Employee) session.getAttribute("employee");
        if(checkEmployee.getRoleId() != 2 && checkEmployee.getRoleId() != 3){
            return "redirect:/home";
        }
        //projekleder(role 2) kan kun se medarbejdere
        if(checkEmployee.getRoleId() == 2){
            List<Employee>getAllCommonWorkers = g1SService.getAllCommonWorkers();
            model.addAttribute("getAllEmployee", getAllCommonWorkers);
        }
        //admins(role 3) kan se alle, medarbejdere, projektledere og admins
        if(checkEmployee.getRoleId() == 3) {
            List<Employee> getAllEmployee = g1SService.getAllEmployee();
            model.addAttribute("getAllEmployee", getAllEmployee);
        }
        return "adminPanel";
    }

    @GetMapping("/admin/addEmployee")
    public String adminPanelAddEmployee(HttpSession session, Model model){
        Employee checkEmployee = (Employee)session.getAttribute("employee");
        //hvis en employee ikke er role 3(admin) eller 2(projektleder), så bliver de redirectet væk fra siden
        if(checkEmployee.getRoleId() != 3 && checkEmployee.getRoleId() != 2){
            return "redirect:/home";
        }
        //tilføjer et nyt employee objekt
        Employee employee = new Employee();
        //hvis den givne employee er role 2(projektleder) så tilgår de siden
        //projektledere kan kun oprette medarbejdere og ikke projektledere og admins
        if(checkEmployee.getRoleId() == 2) {
            employee.setRoleId(1);
        }
        if(checkEmployee.getRoleId() == 3) {
            //Hvis den givne employee er role 3(admin), så tilgår de siden og har mulighed for at ændre roleID
            model.addAttribute("admin", true);
            List<Role> listOfRoles = g1SService.getAllRoles();
            model.addAttribute("roles", listOfRoles);
        }
        model.addAttribute("employee", employee);
        return "adminAddEmployee";
    }

    @PostMapping("/admin/register")
    public String adminRegisterEmployee(@ModelAttribute Employee employee, Model model){
        if(!g1SService.isUsernameFree(employee.getEmployeeUsername())){ //tjekker om brugernavnet er frit
            model.addAttribute("notFree", true);
            return "redirect:/admin/addEmployee";
        }
        System.out.println(employee);
        g1SService.adminRegisterEmployee(employee);
        return "redirect:/adminPanel";
    }

    @PostMapping("/admin/delete/{id}")
    public String adminDeleteEmployee(@PathVariable int id, HttpSession session){

        g1SService.deleteEmployee(id);
        return "redirect:/adminPanel";
    }

    @GetMapping("/admin/update/{id}")
    public String adminUpdateEmployeeGet(@PathVariable int id, HttpSession session, Model model){
        Employee checkEmployee = (Employee)session.getAttribute("employee");
        //hvis en employee ikke er role 3(admin) eller 2(projektleder), så bliver de redirectet væk fra siden
        if(checkEmployee.getRoleId() != 3 && checkEmployee.getRoleId() != 2){
            return "redirect:/home";
        }
        //laver et employee objekt ud fra det id vi får med i URL'en
        Employee oldEmployee = g1SService.getEmployeeById(id);
        model.addAttribute("oldEmployee", oldEmployee);
        //tilføjer et nyt employee objekt
        Employee newEmployee = new Employee();
        //hvis den givne employee er role 2(projektleder) så tilgår de siden
        //projektledere kan kun oprette medarbejdere og ikke projektledere og admins
        if(checkEmployee.getRoleId() == 2) {
            newEmployee.setRoleId(1);
        }
        //Hvis den givne employee er role 3(admin), så tilgår de siden og har mulighed for at ændre roleID
        if(checkEmployee.getRoleId() == 3) {
            model.addAttribute("admin", true);
            List<Role> listOfRoles = g1SService.getAllRoles();
            model.addAttribute("roles", listOfRoles);
        }
        model.addAttribute("newEmployee", newEmployee);
        return "adminUpdateEmployee";
    }

    @PostMapping("/admin/update")
    public String adminUpdateEmployeePost(@ModelAttribute Employee newEmployee, HttpSession session, Model model){
        int employeeID = newEmployee.getEmployeeId();
        if(!g1SService.isUsernameFree(newEmployee.getEmployeeUsername())){ //tjekker om brugernavnet er frit
            model.addAttribute("notFree", true);
            return "redirect:/admin/update/" + employeeID; //hvis det ikke er frit, bliver man smidt tilbage til update siden
        }
        g1SService.updateEmployee(newEmployee);
        return "redirect:/adminPanel";
    }






}
