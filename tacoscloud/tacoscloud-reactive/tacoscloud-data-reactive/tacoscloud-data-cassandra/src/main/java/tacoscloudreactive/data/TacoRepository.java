package tacoscloudreactive.data;

import org.springframework.data.cassandra.repository.AllowFiltering;
import reactor.core.publisher.Mono;
import tacoscloudreactive.domain.Taco;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import java.util.UUID;

public interface TacoRepository extends ReactiveCassandraRepository<Taco, UUID>
{
    @AllowFiltering
    Mono<Taco> findById(String id);
}
