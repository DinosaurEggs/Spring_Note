package tacoscloud.data;

import tacoscloud.domain.Order;
import tacoscloud.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long>//CRUD接口 <持久化类，主键类型>
{
    List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}
