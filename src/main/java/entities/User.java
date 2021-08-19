package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    String id;
    String email;
    String firstName;
    String lastName;
    String password;

    @Override
    public String toString() {
        return id + " " + email + " " + firstName + " " + lastName + " ";
    }
}
