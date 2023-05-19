package todo.project.todotracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import todo.project.todotracker.models.users.Address;
import todo.project.todotracker.models.users.Role;
import todo.project.todotracker.models.users.User;
import todo.project.todotracker.models.users.UserDTO;
import todo.project.todotracker.repositories.RoleRepository;
import todo.project.todotracker.repositories.UserRepository;
import todo.project.todotracker.services.UserService;
import todo.project.todotracker.utils.RoleType;


@SpringBootApplication
public class TodotrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodotrackerApplication.class, args);
		System.out.println("...App is running.");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Autowired
	RoleRepository roleRepository;
	@Autowired
	UserRepository userRepository;


	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			/* ROLE CREATION
			 * Sets up user roles (see enum: RoleTypes) when the application runs for the first time or no Roles are present in the database.
			 * */
			if(roleRepository.findAll().isEmpty()){
				roleRepository.save(new Role(RoleType.ADMIN));
				roleRepository.save(new Role(RoleType.BASIC));
			}

			/* DEFAULT ADMIN USER CREATION
			 * Sets up a default Admin user (user / 1234) when running the application for the first time or no Users are present in the database.
			 */
			if(userRepository.findAll().isEmpty() || userRepository.findByRole(roleRepository.findByRoleType(RoleType.ADMIN).get()).isEmpty()){
				userService.createUser(new UserDTO("Name","user","1234","ADMIN","Spain", "Barcelona", "Street", "000"));
			}

		};
	}

}
