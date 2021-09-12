package tacoscloud.entitymodel.ingredient;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import tacoscloud.domain.Ingredient;


public class IngredientEntityModelAssembler extends RepresentationModelAssemblerSupport<Ingredient, IngredientEntityModel>
{
    public IngredientEntityModelAssembler()
    {
        super(IngredientPath.class, IngredientEntityModel.class);
    }

    @Override
    protected IngredientEntityModel instantiateModel(Ingredient entity)
    {
        return new IngredientEntityModel(entity);
    }

    @Override
    public IngredientEntityModel toModel(Ingredient entity)
    {
        return createModelWithId(entity.getId(), entity);
    }
}

@Controller
@RequestMapping("/api/rest/ingredients")
class IngredientPath
{
}
