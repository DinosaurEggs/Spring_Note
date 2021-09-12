package tacoscloud.data;

import tacoscloud.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String>
{
    User findByUsername(String username);
}
