package tacoscloud.entitymodel.taco;

import lombok.Getter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import tacoscloud.domain.Taco;
import tacoscloud.entitymodel.ingredient.IngredientEntityModel;
import tacoscloud.entitymodel.ingredient.IngredientEntityModelAssembler;

import java.util.Date;

@Relation(//指定 Spring HATEOAS 应该如何在 JSON 结果中字段的命名
        value = "taco",//单个 EntityModel 名称
        collectionRelation = "tacos"//多个 CollectionModel 名称
)
//将taco对象转换为新的TacoEntityModel对象,够携带链接
//URL: https://blog.csdn.net/qq_42799615/article/details/110791488
public class TacoEntityModel extends RepresentationModel<TacoEntityModel>
{
    private static final IngredientEntityModelAssembler ingredientEntityModelAssembler = new IngredientEntityModelAssembler();

    @Getter
    private final String name;

    @Getter
    private final Date createdAt;

    @Getter
    private final CollectionModel<IngredientEntityModel> ingredients;

    public TacoEntityModel(Taco taco)
    {
        this.name = taco.getName();
        this.createdAt = taco.getCreatedAt();
        this.ingredients = ingredientEntityModelAssembler.toCollectionModel(taco.getIngredients());
/*      cassandra
        this.ingredients = ingredientEntityModelAssembler.toCollectionModel(()->
        {
            List<Ingredient> ingredients = new ArrayList<>();
            for(IngredientUDT ingredientUDT:taco.getIngredients()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(ingredientUDT.getType().name());
                ingredient.setName(ingredientUDT.getName());
                ingredient.setType(ingredientUDT.getType());
                ingredients.add(ingredient);
            }
            return ingredients.listIterator();
        });*/
    }
}
