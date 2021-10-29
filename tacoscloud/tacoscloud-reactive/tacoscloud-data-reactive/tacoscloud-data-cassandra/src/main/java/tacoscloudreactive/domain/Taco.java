package tacoscloudreactive.domain;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import lombok.Data;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Table("taco")
public class Taco implements Serializable
{
    @PrimaryKeyColumn(type= PrimaryKeyType.PARTITIONED)
    private UUID id = Uuids.timeBased();

    private Date createdAt = new Date();//db_type: timestamp

    @NotNull
    @Size(min = 2, message = "Name must be at least 2 characters long")
    private String name;

    @NotNull(message = "You must choose at least 1 ingredient")
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    @Column("ingredient")
    private List<IngredientUDT> ingredients = new ArrayList<>();//db_type: list<frozen<ingredient>>

    public TacoUDT toUDT()
    {
        return new TacoUDT(name,ingredients);
    }
}
/*
create table taco
(
    id uuid primary key,
    createdat timestamp,
    name text,
    ingredients list<frozen<ingredient>>
);
 */