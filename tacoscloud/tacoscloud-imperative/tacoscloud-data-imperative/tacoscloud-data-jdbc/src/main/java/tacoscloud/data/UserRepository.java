package tacoscloud.data;

import tacoscloud.domain.User;

public interface UserRepository
{
    User findByUsername(String username);
    User findById(Long id);
    User save(User user);
}
