package tacoscloud.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import tacoscloud.data.IngredientRepository;
import tacoscloud.domain.Ingredient;

import java.util.ArrayList;
import java.util.List;

@Service
public class IngredientLoader
{
    private final List<Ingredient> ingredients = new ArrayList<>();

    public IngredientLoader(IngredientRepository ingredientRepository)
    {
        ingredientRepository.findAll();
    }

    @Bean
    public List<Ingredient> getIngredients()
    {
        return ingredients;
    }
}
