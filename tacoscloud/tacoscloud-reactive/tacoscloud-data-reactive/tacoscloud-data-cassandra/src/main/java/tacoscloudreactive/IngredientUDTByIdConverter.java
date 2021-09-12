package tacoscloudreactive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import tacoscloudreactive.data.IngredientRepository;
import tacoscloudreactive.domain.Ingredient;
import tacoscloudreactive.domain.IngredientUDT;

@Component
public class IngredientUDTByIdConverter implements Converter<String, IngredientUDT>
{
    private final IngredientRepository ingredientRepo;

    @Autowired
    public IngredientUDTByIdConverter(IngredientRepository ingredientRepo)
    {
        this.ingredientRepo = ingredientRepo;
    }
    @Override
    public IngredientUDT convert(String s)
    {
        Ingredient ingredient = ingredientRepo.findById(s).block();
        return new IngredientUDT(ingredient.getName(), ingredient.getType());
    }
}
