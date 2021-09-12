package tacoscloudreactive.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import tacoscloudreactive.domain.Order;
import tacoscloudreactive.domain.User;

public interface OrderRepository extends ReactiveMongoRepository<Order, String>
{
    Flux<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}
