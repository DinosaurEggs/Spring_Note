package tacoscloudreactive.data;

import tacoscloudreactive.domain.Ingredient;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

public interface IngredientRepository extends ReactiveCassandraRepository<Ingredient, String>
{
}
