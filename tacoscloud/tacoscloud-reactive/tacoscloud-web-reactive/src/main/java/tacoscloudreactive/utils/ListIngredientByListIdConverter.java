package tacoscloudreactive.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import tacoscloudreactive.domain.Ingredient;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListIngredientByListIdConverter implements Converter<List<String>, List<Ingredient>>
{
    private final List<Ingredient> ingredients;

    @Autowired
    public ListIngredientByListIdConverter(List<Ingredient> ingredients)
    {
        this.ingredients = ingredients;
    }

    /*响应式方法请求此转换器*/
    @Override
    public List<Ingredient> convert(List<String> id)
    {
        return ingredients.stream().filter(ingredient -> id.contains(ingredient.getId())).collect(Collectors.toList());
    }
}
