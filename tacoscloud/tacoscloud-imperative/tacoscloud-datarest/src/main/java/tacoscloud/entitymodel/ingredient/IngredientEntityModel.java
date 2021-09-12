package tacoscloud.entitymodel.ingredient;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import tacoscloud.domain.Ingredient;


@SuppressWarnings("FieldMayBeFinal")
@Relation(value = "ingredient", collectionRelation = "ingredients")
public class IngredientEntityModel extends RepresentationModel<IngredientEntityModel>
{
    @Getter
    private final String name;

    @Getter
    private final Ingredient.Type type;

    public IngredientEntityModel(Ingredient ingredient)
    {
        this.name = ingredient.getName();
        this.type = ingredient.getType();
    }
}
