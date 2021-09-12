package tacoscloudreactive.domain;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.List;

@Data
@UserDefinedType("taco")
public class TacoUDT {
  
  private final String name;
  private final List<IngredientUDT> ingredients;
  
}
/*
create type tacoscloud.taco(
      name text,
      ingredients list<frozen<tacoscloud.ingredient>>
      )
 */
