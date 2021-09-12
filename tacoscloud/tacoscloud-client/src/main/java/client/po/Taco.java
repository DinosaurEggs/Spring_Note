package client.po;

import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Taco implements Serializable
{
    private String id;

    private Date createdAt = new Date();

    private String name;

    private List<Ingredient> ingredients = new ArrayList<>();
}
