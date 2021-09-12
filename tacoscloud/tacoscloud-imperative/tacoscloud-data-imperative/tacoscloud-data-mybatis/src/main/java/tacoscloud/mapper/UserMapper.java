package tacoscloud.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import tacoscloud.domain.User;

@Mapper
public interface UserMapper
{
    @Select("select phonenumber, id, username, password from users where username=#{username}")
    User findByUsername(String username);

    @Select("select phonenumber, id, username, password from users where id=#{id}")
    User findById(Long id);

    /* Oracle https://zhuanlan.zhihu.com/p/81183888
      @SelectKey(statement = "select SEQ_XXX_ from DUAL", keyProperty = "user.id", before = true, resultType = Long.class)
      @Update("update into users(id, phonenumber, username, password) values (#{id}, #{phonenumber}, #{username}, #{password})")
    */
    @Insert("insert into users(phonenumber, username, password) values (#{phoneNumber}, #{username}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void save(User user);

}
