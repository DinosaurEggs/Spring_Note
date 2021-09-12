package tacoscloudreactive.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@Document(collection = "ingredient")
public class Ingredient implements Serializable
{
    @Id
    private String _id;

    private String id;

    private String name;

    private Type type;

    public enum Type
    {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
