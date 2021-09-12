package tacoscloud.domain;

import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Taco implements Serializable
{
    private long id;

    private Date createdAt;

    private String name;

    private List<Ingredient> ingredients = new ArrayList<>();

}
