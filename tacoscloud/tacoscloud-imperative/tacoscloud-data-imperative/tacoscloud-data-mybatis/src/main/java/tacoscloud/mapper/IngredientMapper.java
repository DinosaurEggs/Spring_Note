package tacoscloud.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import tacoscloud.domain.Ingredient;

import java.util.List;
import java.util.Optional;

@Mapper
@SuppressWarnings("unused")
public interface IngredientMapper
{
    @Select("select * from ingredient")
    List<Ingredient> findAll();

    @Select("select name, type, id from ingredient where id=any(select ingredient_id from taco_ingredient where taco_id=#{tacoId})")
    List<Ingredient> findByTacoId(long tacoId);

    @Select("select * from ingredient where id=#{id}")
    Optional<Ingredient> findById(String id);

    @Insert("insert into ingredient (id, name, type) values (#{id}, #{name}, #{type})")
    void save(Ingredient ingredient);
}
