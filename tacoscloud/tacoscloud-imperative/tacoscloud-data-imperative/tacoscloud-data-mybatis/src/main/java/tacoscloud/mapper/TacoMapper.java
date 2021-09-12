package tacoscloud.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tacoscloud.domain.Taco;

import java.util.List;
import java.util.Optional;

@Mapper
@SuppressWarnings("unused")
public interface TacoMapper
{
    @Select("select createdat, name, id from taco")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "ingredients", column = "id",
                    many = @Many(select = "tacoscloud.mapper.IngredientMapper.findByTacoId"))
    })
    List<Taco> findAll();

    @Select("select createdat, name, id from taco limit #{pageNumber}, #{pageSize}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "ingredients", column = "id",
                    many = @Many(select = "tacoscloud.mapper.IngredientMapper.findByTacoId"))
    })
    List<Taco> findAllPaged(@Param("pageNumber") int pageNumber, @Param("pageSize") int pageSize);

    @Select("select createdat, name, id from taco where id=any(select taco_id from order_taco where order_id=#{orderId})")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "ingredients", column = "id",
                    many = @Many(select = "tacoscloud.mapper.IngredientMapper.findByTacoId"))
    })
    List<Taco> findByOrderId(Long orderId);

    @Select("select createdat, name, id from taco where id=#{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "ingredients", column = "id",
                    many = @Many(select = "tacoscloud.mapper.IngredientMapper.findByTacoId"))
    })
    Optional<Taco> findById(Long id);


    @Insert("insert into taco (createdat, name) values (#{taco.createdAt}, #{taco.name})")
    @Options(useGeneratedKeys = true, keyProperty = "taco.id", keyColumn = "id")
    void save(@Param("taco") Taco taco);

    @Insert("insert into taco_ingredient (taco_id, ingredient_id) values (#{tacoId}, #{ingredientId})")
    void saveTacoIngredient(Long tacoId, String ingredientId);

    @Delete("delete from orders where id=#{id}")
    void deleteById(Long id);
}
