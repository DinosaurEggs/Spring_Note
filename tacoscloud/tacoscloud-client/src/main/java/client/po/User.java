package client.po;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class User implements Serializable
{
    private String id;

    private String username;

    private String password;

    private String phoneNumber;

    public User(String username, String password, String phoneNumber)
    {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public User(String id, String username, String password, String phoneNumber)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
