package tacoscloud.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class Taco implements Serializable
{
    public Taco(long id, Date createdAt, String name,List<Ingredient> ingredients)
    {
        this.id = id;
        this.createdAt = createdAt;
        this.name = name;
        this.ingredients=ingredients;
    }

    private long id;

    private Date createdAt;

    @NotNull
    @Size(min = 2, message = "Name must be at least 2 characters long")
    private String name;

    @NotNull(message = "You must choose at least 1 ingredient")
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    private List<Ingredient> ingredients = new ArrayList<>();

    public void setCreateDat()
    {//将 createdAt 属性设置为保存 Taco 之前的当前日期和时间
        this.createdAt = new Date();
    }
}
