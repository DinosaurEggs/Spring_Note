package tacoscloud.domain;

import lombok.Data;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "ORDERS")// Order 在 SQL 中是一个保留字，会导致问题
@RestResource(
        rel = "order",
        path = "order"
)
public class Order implements Serializable
{
    @Id
    @Column(name = "ID")
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
//            , generator = "SEQ_TACO_ORDER_IDENTITY"//使用GenerationType.SEQUENCE
    )
    private Long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "USERID", foreignKey = @ForeignKey(name = "USER_ID"))
    private User user;

    @Column(name = "PLACEDAT")
    private Date placedAt;

    @NotBlank(message = "Delivery name is required")
    @Column(name = "DELIVERYNAME")
    private String deliveryName;

    @NotBlank(message = "Street is required")
    @Column(name = "DELIVERYSTREET")
    private String deliveryStreet;

    @NotBlank(message = "City is required")
    @Column(name = "DELIVERYCITY")
    private String deliveryCity;

    @NotBlank(message = "State is required")
    @Column(name = "DELIVERYSTATE")
    private String deliveryState;

    @NotBlank(message = "Zip code is required")
    @Column(name = "DELIVERYZIP")
    private String deliveryZip;

    //    @CreditCardNumber(message="Not a valid credit card number")
    @NotBlank(message = "Credit card number is required")
    @Column(name = "CCNUMBER")
    private String ccNumber;

    @Pattern(regexp = "^(0[1-9]|1[0-2])([/])([1-9][0-9])$",
            message = "Must be formatted MM/YY")
    @Column(name = "CCEXPIRATION")
    private String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    @Column(name = "CCCVV")
    private String ccCVV;

    @OneToMany(
            targetEntity = Taco.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL //级联权限设置 https://blog.csdn.net/u014371184/article/details/90902320
    )
    @JoinTable(
            name = "ORDER_TACO",
            joinColumns = {@JoinColumn(name = "ORDER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "TACO_ID")},
            foreignKey = @ForeignKey(name = "ORDER_ID"),
            inverseForeignKey = @ForeignKey(name = "TACO_ID_INV")
    )
    private List<Taco> tacos = new ArrayList<>();

    public void addDesign(Taco design)
    {
        this.tacos.add(design);
    }

    @PrePersist
    void placedAt()
    {
        this.placedAt = new Date();
    }
}
