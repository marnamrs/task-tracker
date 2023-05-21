package todo.project.todotracker.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import todo.project.todotracker.models.tasks.Task;
import todo.project.todotracker.models.users.Address;
import todo.project.todotracker.models.users.User;
import todo.project.todotracker.models.users.UserDTO;
import todo.project.todotracker.repositories.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@Slf4j
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    /** Takes a userDTO and instantiates a User after verifying there are no users with the given username in the database.
     * @param userDTO object with all properties necessary to instantiate user Address and create the new User
     * @return User as saved in the database (with encoded password)
     * @throws ResponseStatusException HttpStatus.CONFLICT if the username already exists in the database
     * &#064;See  userService.saveUser(User user)
     */
    public User createUser(UserDTO userDTO){
        if(userRepository.findByUsername(userDTO.getUsername()).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already taken.");
        }
        log.info("Creating new user: {}", userDTO.getUsername());
        Address address = new Address(userDTO.getCountry(), userDTO.getCity(), userDTO.getStreet(), userDTO.getZipCode());
        User user = new User(userDTO.getName(), userDTO.getUsername(), userDTO.getPassword(), address);
        return saveUser(user);
    }

    private User saveUser(User user) {
        log.info("Saving new user: {}", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    /** Fetch all users from the database and return a list of usernames.
     * @return List<String> of usernames
     * @throws ResponseStatusException "NOT_FOUND" if no users are present in the database
     */
    public List<User> getAllUsers(){
        log.info("Fetching Users");
        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No users found");
        }
        //List<String> usernames = users.stream().map(User::getUsername).toList();
        return users;
    }

    public User getUserById(Long id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return user.get();
    }

    public boolean verifyOwnership(Task task, String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return Objects.equals(task.getUser().getId(), user.get().getId());
    }
}
