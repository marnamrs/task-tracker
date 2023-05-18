package todo.project.todotracker.models.users;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDTO {

    //User required data:

    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private String username;
    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String roleName; //in UserService layer Role is fetched by roleName from roleRepository

    //Address required data:

    @NotNull
    @NotEmpty
    private String country;
    @NotNull
    @NotEmpty
    private String city;
    @NotNull
    @NotEmpty
    private String street;
    @NotNull
    @NotEmpty
    private String zipCode;



}
