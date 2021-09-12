package tacoscloud.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class User implements UserDetails, Serializable
{
    private Long id;

    private final String username;

    private final String password;

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
