package tacoscloudreactive.data;

import org.springframework.data.cassandra.repository.AllowFiltering;
import reactor.core.publisher.Mono;
import tacoscloudreactive.domain.Order;
import tacoscloudreactive.domain.User;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface OrderRepository extends ReactiveCassandraRepository<Order, UUID>
{
    @AllowFiltering
    Flux<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);

    @AllowFiltering
    Mono<Order> findById(String id);

    @SuppressWarnings("UnusedReturnValue")
    @AllowFiltering
    Mono<Order> deleteById(String id);
}
