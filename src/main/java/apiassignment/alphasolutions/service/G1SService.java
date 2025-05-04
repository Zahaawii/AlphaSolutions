package apiassignment.alphasolutions.service;

import apiassignment.alphasolutions.repository.G1SRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class G1SService {

    @Autowired
    private G1SRepository g1SRepository;
}
