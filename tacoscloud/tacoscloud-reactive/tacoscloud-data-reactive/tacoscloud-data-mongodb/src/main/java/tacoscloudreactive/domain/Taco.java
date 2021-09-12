package tacoscloudreactive.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "taco")
public class Taco implements Serializable
{
    @Id
    private String id;

    private Date createdAt = new Date();

    @NotNull
    @Size(min = 2, message = "Name must be at least 2 characters long")
    private String name;

    @NotNull(message = "You must choose at least 1 ingredient")
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    private List<Ingredient> ingredients = new ArrayList<>();
}
