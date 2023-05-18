package todo.project.todotracker.models.users;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Address {
    private String country;
    private String city;
    private String street;
    private String zipCode;
}
