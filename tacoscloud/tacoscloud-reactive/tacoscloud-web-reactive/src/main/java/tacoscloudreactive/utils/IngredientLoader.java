package tacoscloudreactive.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import tacoscloudreactive.data.IngredientRepository;
import tacoscloudreactive.domain.Ingredient;

import java.util.ArrayList;
import java.util.List;

@Service
public class IngredientLoader
{
    private final List<Ingredient> ingredients = new ArrayList<>();

    public IngredientLoader(IngredientRepository ingredientRepository)
    {
        ingredientRepository.findAll().subscribe(ingredients::add);
    }

    @Bean
    public List<Ingredient> getIngredients()
    {
        return ingredients;
    }
}
