package tacoscloudreactive.data;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import tacoscloudreactive.domain.User;

public interface UserRepository extends ReactiveMongoRepository<User, String>
{
    Mono<User> findByUsername(String username);
}
