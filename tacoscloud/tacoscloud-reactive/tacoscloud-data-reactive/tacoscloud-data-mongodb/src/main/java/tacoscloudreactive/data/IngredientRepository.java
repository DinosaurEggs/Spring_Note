package tacoscloudreactive.data;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import tacoscloudreactive.domain.Ingredient;

public interface IngredientRepository extends ReactiveMongoRepository<Ingredient, String>
{
}
