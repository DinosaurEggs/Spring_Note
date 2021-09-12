package tacoscloud.data;

import tacoscloud.domain.Order;
import tacoscloud.domain.User;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface OrderRepository
{
    List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
    Optional<Order> findById(Long id);
    Order save(Order order);
    void deleteById(Long id);

//    List<Order> findByUserOrderByPlacedAtDesc(User user);

}
