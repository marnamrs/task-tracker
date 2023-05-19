package todo.project.todotracker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class TodotrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodotrackerApplication.class, args);
		System.out.println("...App is running.");
	}

	@Bean
	CommandLineRunner run() {
		//TODO add plug to userService
		return args -> {
			/**
			 * Set up user roles (see enum: RoleTypes) when the application runs for the first time.
			 * */

			//TODO add saveRole

			//TODO add creation of default User for demo
		};
	}

}
