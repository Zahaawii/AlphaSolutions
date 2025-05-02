package apiassignment.alphasolutions.service;

import apiassignment.alphasolutions.model.Employee;
import apiassignment.alphasolutions.repository.G1SRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class G1SService {
    private final G1SRepository g1SRepository;

    public G1SService(G1SRepository g1SRepository){
        this.g1SRepository = g1SRepository;
    }

    public Employee login(String username, String password){
        return g1SRepository.login(username, password);
    }

    public List<Employee> getAllEmployee(){
        return g1SRepository.getAllEmployee();
    }

    public boolean isUsernameFree(String username){
        return g1SRepository.isUsernameFree(username);
    }

    public Employee adminRegisterEmployee(Employee employee){
        return g1SRepository.adminRegisterEmployee(employee);
    }

    }
