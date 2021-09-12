package tacoscloud.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Ingredient implements Serializable
{
    public Ingredient(String id, String name, Type type)
    {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    private String id;
    private String name;
    private Type type;

    public enum Type
    {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
