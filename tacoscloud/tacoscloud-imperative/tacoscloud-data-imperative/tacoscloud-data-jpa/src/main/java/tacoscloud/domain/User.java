package tacoscloud.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

@Data
@Entity
@Table(name = "USERS")
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class User implements UserDetails, Serializable
{
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
//            ,generator = "SEQ_USER_IDENTITY"
    )
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERNAME")
    private final String username;
    @Column(name = "PASSWORD")
    private final String password;
    @Column(name = "PHONENUMBER")
    private final String phoneNumber;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
//        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));//单元素集合
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
