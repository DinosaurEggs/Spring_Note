package tacoscloud.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data//自动生成gatter equals等
@RequiredArgsConstructor//生成构造方法 如果带参数，这参数只能是以final修饰的未经初始化的字段或者是以@NonNull注解的未经初始化的字段
//@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)//生成无参数构造函数 //JPA 要求实体有一个无参构造函数
@Entity//声明为 JPA 实体
@Table(name = "INGREDIENT")
public class Ingredient implements Serializable
{
    @Id//声明主键
    private String id;
    @Column(name = "NAME")
    private String name;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type
    {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }

//    @Transient //该属性并非一个到数据库表的字段的映射
//    private int a;
}
