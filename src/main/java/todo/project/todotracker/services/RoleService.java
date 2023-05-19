package todo.project.todotracker.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import todo.project.todotracker.repositories.RoleRepository;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;


}
