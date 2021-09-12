package tacoscloudreactive.domain;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Table("user")
public class User implements UserDetails, Serializable
{
    @PrimaryKeyColumn(type= PrimaryKeyType.PARTITIONED)
    private UUID id = Uuids.timeBased();

    private String username;

    private String password;

    private String phoneNumber;

    public User(String username, String password, String phoneNumber)
    {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public User(UUID id, String username, String password, String phoneNumber)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }
}
