package tacoscloudreactive.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;
import tacoscloudreactive.domain.Ingredient;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@UserDefinedType("ingredient")
public class IngredientUDT {
  private final String name;
  private final Ingredient.Type type;
}
/*
create type tacoscloud.ingredient(
      name text,
      type text
      )
*/