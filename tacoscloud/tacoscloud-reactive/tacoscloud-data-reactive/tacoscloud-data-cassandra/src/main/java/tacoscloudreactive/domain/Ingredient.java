package tacoscloudreactive.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@Table("ingredient")
public class Ingredient implements Serializable
{
    @PrimaryKey
    private String id;

    private String name;

    private Type type;

    public enum Type
    {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }

    public IngredientUDT toUDT()
    {
        return new IngredientUDT(name, type);
    }
}
