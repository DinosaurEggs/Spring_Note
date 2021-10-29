package tacoscloudreactive.domain;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
@Data
@Table("orders")
public class Order implements Serializable
{
    private static final long serialVersionUID = 1L;

    @PrimaryKey
    private UUID id = Uuids.timeBased();

    @Column("user")
    private UserUDT user;//db_type: frozen<user>

    private Date placedAt = new Date();//db_type: timestamp

    @NotBlank(message = "Delivery name is required")
    private String deliveryName;

    @NotBlank(message = "Street is required")
    private String deliveryStreet;

    @NotBlank(message = "City is required")
    private String deliveryCity;

    @NotBlank(message = "State is required")
    private String deliveryState;

    @NotBlank(message = "Zip code is required")
    private String deliveryZip;

    @NotBlank(message = "Credit card number is required")
    private String ccNumber;

    @Pattern(regexp = "^(0[1-9]|1[0-2])([/])([1-9][0-9])$", message = "Must be formatted MM/YY")
    private String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    private String ccCVV;

    @Column("taco")
    private List<TacoUDT> tacos = new ArrayList<>();//db_type: list<frozen<taco>>

    public void addDesign(Taco design)
    {
        this.tacos.add(design.toUDT());
    }

    public void setUser(User user)
    {
        this.user = user.toUDT();
    }
}
/*
create table orders
(
    id uuid primary key,
    user frozen<user>,
    createdat timestamp,
    deliveryname text,
    deliverystreet text,
    deliverycity text,
    deliverystate text,
    deliveryzip text,
    ccnumber text,
    ccexpiration text,
    cccvv text,
    tacos list<frozen<taco>>
);
 */
