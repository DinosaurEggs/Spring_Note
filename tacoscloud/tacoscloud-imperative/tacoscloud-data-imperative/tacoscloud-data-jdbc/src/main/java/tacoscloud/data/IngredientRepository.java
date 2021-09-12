package tacoscloud.data;

import tacoscloud.domain.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository
{
    List<Ingredient> findAll();
    Optional<Ingredient> findById(String id);
    List<Ingredient> findByTacoId(long tacoId);
    Ingredient save(Ingredient ingredient);
}
