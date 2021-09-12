package tacoscloud.data.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import tacoscloud.data.UserRepository;
import tacoscloud.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Repository
public class JdbcUserRepository implements UserRepository
{
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert userInsert;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
        this.userInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public User findByUsername(String username)
    {
        return jdbcTemplate.queryForObject("select phonenumber, id, username, password from users where username=?", this::mapRowToUser, username);
    }

    @Override
    public User findById(Long id)
    {
        return jdbcTemplate.queryForObject("select phonenumber, id, username, password from users where id=?", this::mapRowToUser, id);
    }

    @Override
    public User save(User user)
    {
        Map<String,Object> values = new HashMap<>();
        values.put("username", user.getUsername());
        values.put("password",user.getPassword());
        values.put("phonenumber", user.getPhoneNumber());

        Long id = userInsert.executeAndReturnKey(values).longValue();
        user.setId(id);
        return user;
    }

    private User mapRowToUser(ResultSet rs, int num) throws SQLException
    {
        User user = new User(
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("phonenumber")
        );
        user.setId(rs.getLong("id"));
        return user;
    }
}
