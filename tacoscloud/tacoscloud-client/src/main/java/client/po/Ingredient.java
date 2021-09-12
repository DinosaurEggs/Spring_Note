package client.po;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
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
