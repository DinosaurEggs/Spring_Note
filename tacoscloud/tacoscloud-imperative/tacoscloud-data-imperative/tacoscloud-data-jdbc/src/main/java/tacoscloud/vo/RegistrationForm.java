package tacoscloud.vo;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import tacoscloud.domain.User;

import java.io.Serializable;

@Data
public class RegistrationForm implements Serializable
{
    private String username;
    private String password;
    private String phoneNumber;

    public User toUser(PasswordEncoder passwordEncoder)
    {
        return new User(username, passwordEncoder.encode(password), phoneNumber);
    }
}
