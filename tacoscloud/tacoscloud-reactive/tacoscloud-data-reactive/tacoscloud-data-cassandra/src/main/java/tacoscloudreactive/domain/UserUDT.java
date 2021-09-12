package tacoscloudreactive.domain;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType("user")
@Data
public class UserUDT {
  
  private final String username;
  private final String phoneNumber;
  
}
/*
create type tacoscloud.user(
      username text,
      phonenumber text
      )
 */