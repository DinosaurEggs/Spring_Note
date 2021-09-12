package tacoscloud.domain;

import lombok.Data;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "TACO")
@RestResource(// Spring Data REST 中调整关系名称和路径
        rel = "tacos",//生成资源链接的路径名称
        path = "tacos"//rest的路径链接名称
)
public class Taco implements Serializable
{
    @Id
    @Column(name = "ID")
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
//            , generator = "SEQ_TACO_IDENTITY"
    )
    private long id;

    @Column(name = "CREATEDAT")
    private Date createdAt;

    @NotNull
    @Size(min = 2, message = "Name must be at least 2 characters long")
    @Column(name = "NAME")
    private String name;

    @ManyToMany(targetEntity = Ingredient.class, fetch = FetchType.EAGER)//声明 Taco 及其相关 Ingredient 列表之间的关系
    @JoinTable(
            name = "TACO_INGREDIENT",
            joinColumns = {@JoinColumn(name = "TACO_ID")},//外键列 参照 当前实体 对应表的主键列
            inverseJoinColumns = {@JoinColumn(name = "INGREDIENT_ID")},//外键列 参照当前实体的 关联实体 对应表的主键列
            foreignKey = @ForeignKey(name = "TACO_ID"),
            inverseForeignKey = @ForeignKey(name = "INGREDIENT_ID_INV")
    )
    @NotNull(message = "You must choose at least 1 ingredient")
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    private List<Ingredient> ingredients = new ArrayList<>();

    @PrePersist
    void createAt()
    {
        //将 createdAt 属性设置为保存 Taco 之前的当前日期和时间
        this.createdAt = new Date();
    }
}
