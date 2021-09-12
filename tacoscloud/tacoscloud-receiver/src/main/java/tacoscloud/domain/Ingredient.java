package tacoscloud.domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class Ingredient implements Serializable
{

    private String id;

    private String name;

    private Type type;

    public enum Type
    {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }

}
