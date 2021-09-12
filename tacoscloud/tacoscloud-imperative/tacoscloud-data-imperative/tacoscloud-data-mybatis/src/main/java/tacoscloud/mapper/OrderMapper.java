package tacoscloud.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import tacoscloud.domain.Order;

import java.util.List;
import java.util.Optional;

@Mapper
@Service
public interface OrderMapper
{
    @Select("select * from orders where userid=#{userId} order by placedat desc limit #{pageNumber}, #{pageSize}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "user", column = "userid",
                    one = @One(select = "tacoscloud.mapper.UserMapper.findById")),
            @Result(property = "tacos", column = "id",
                    many = @Many(select = "tacoscloud.mapper.TacoMapper.findByOrderId"))
    })
    List<Order> findByUserOrderByPlacedAtDesc(@Param("userId") long userId, @Param("pageNumber") int pageNumber, @Param("pageSize") int pageSize);

    @Select("select * from orders where id=#{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "user", column = "userid",
                    one = @One(select = "tacoscloud.mapper.UserMapper.findById")),
            @Result(property = "tacos", column = "id",
                    many = @Many(select = "tacoscloud.mapper.TacoMapper.findByOrderId"))
    })
    Optional<Order> findById(Long id);

    @Insert("insert into " +
            "orders (cccvv, ccexpiration, ccnumber, deliverycity, deliveryname, deliverystate, deliverystreet, deliveryzip, placedat, userid) " +
            "values (#{order.ccCVV}, #{order.ccExpiration}, #{order.ccNumber}, #{order.deliveryCity}, #{order.deliveryName}, #{order.deliveryState}, #{order.deliveryStreet}, #{order.deliveryZip}, #{order.placedAt}, #{order.user.id})"
    )
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "order.id")
    void save(@Param("order") Order order);

    @Insert("insert into order_taco (order_id, taco_id) values (#{orderId}, #{tacoId})")
    void saveOrderTaco(long orderId, long tacoId);

    @Delete("delete from orders where id=#{id}")
    void deleteById(Long id);
}
