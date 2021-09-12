package client.po;

import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("unused")
@Data
public class Order implements Serializable
{
    private String id;

    private User user;

    private Date placedAt = new Date();

    private String deliveryName;

    private String deliveryStreet;

    private String deliveryCity;

    private String deliveryState;

    private String deliveryZip;

    private String ccNumber;

    private String ccExpiration;

    private String ccCVV;

    private List<Taco> tacos = new ArrayList<>();

    public void addDesign(Taco design)
    {
        this.tacos.add(design);
    }

}
