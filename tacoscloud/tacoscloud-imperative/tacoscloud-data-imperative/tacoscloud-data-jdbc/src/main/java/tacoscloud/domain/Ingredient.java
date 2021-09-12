package tacoscloud.domain;

import lombok.Data;

import java.io.Serializable;

@Data
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
