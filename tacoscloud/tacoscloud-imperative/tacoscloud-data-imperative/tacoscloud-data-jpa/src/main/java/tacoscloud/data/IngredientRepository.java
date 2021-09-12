package tacoscloud.data;


import tacoscloud.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String>//CRUD接口 <持久化类，主键类型>
{
}
