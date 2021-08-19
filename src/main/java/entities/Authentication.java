package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class Authentication {
    String username;
    String password;
    Boolean skipOtpCheck;

    public Authentication() {
        setUsername("mainesedoardo@gmail.com");
        setPassword("Tirocinio99Swag!");
        setSkipOtpCheck(true);
    }

    @Override
    public String toString() {
        return "{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", skipOtpCheck=" + skipOtpCheck +
                '}';
    }
}
