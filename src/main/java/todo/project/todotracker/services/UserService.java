package todo.project.todotracker.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import todo.project.todotracker.models.users.Address;
import todo.project.todotracker.models.users.Role;
import todo.project.todotracker.models.users.User;
import todo.project.todotracker.models.users.UserDTO;
import todo.project.todotracker.repositories.RoleRepository;
import todo.project.todotracker.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    User createUser(UserDTO userDTO){
        if(userRepository.findByUsername(userDTO.getUsername()).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already taken.");
        }
        if(roleRepository.findByName(userDTO.getRoleName()).isEmpty()){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found");
        }
        log.info("Creating new user {}", userDTO.getUsername());
        Role role = roleRepository.findByName(userDTO.getRoleName()).get();
        Address address = new Address(userDTO.getCountry(), userDTO.getCity(), userDTO.getStreet(), userDTO.getZipCode());
        User user = new User(userDTO.getName(), userDTO.getUsername(), userDTO.getPassword(), address, role);
        return saveUser(user);
    }

    private User saveUser(User user) {
        log.info("Saving new user...");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    /** Fetch all users from the database and return a list of usernames.
     * @return List<String> of usernames
     * @throws HttpStatus "NOT_FOUND" if no users are present in the database
     */
    List<String> getAllUsers(){
        log.info("Fetching Users");
        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No users found");
        }
        List<String> usernames = users.stream().map(User::getUsername).toList();
        return usernames;
    }
}
