package tacoscloudreactive.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tacoscloudreactive.data.UserRepository;

@Service
public class UserRepositoryUserDetailsService implements ReactiveUserDetailsService
{
    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryUserDetailsService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username)
    {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User Not Found")))
                .map(UserDetails.class::cast);
    }
}
