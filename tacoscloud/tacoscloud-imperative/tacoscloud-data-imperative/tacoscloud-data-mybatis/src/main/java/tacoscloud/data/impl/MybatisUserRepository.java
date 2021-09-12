package tacoscloud.data.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tacoscloud.data.UserRepository;
import tacoscloud.domain.User;
import tacoscloud.mapper.UserMapper;

@Repository
public class MybatisUserRepository implements UserRepository
{
    private final UserMapper userMapper;

    public MybatisUserRepository(UserMapper userMapper)
    {
        this.userMapper = userMapper;
    }

    @Override
    public User findByUsername(String username)
    {
        return userMapper.findByUsername(username);
    }

    @Override
    public User findById(Long id)
    {
        return userMapper.findById(id);
    }

    @Override
    @Transactional
    public User save(User user)
    {
        userMapper.save(user);
        return user;
    }
}
