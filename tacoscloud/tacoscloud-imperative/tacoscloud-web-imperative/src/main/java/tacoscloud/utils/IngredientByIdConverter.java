package tacoscloud.utils;

import tacoscloud.data.IngredientRepository;
import tacoscloud.domain.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

/*阻塞式方法请求此转换器*/
@Component
public class IngredientByIdConverter implements Converter<String, Ingredient>
{
    private final IngredientRepository ingredientRepo;

    @Autowired
    public IngredientByIdConverter(IngredientRepository ingredientRepo)
    {
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public Ingredient convert(String id)
    {
        Optional<Ingredient> optionalIngredient = ingredientRepo.findById(id);
        return optionalIngredient.orElse(null);
    }
}