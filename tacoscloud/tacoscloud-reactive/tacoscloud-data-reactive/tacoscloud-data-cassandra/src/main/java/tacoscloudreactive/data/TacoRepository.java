package tacoscloudreactive.data;

import tacoscloudreactive.domain.Taco;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import java.util.UUID;

public interface TacoRepository extends ReactiveCassandraRepository<Taco, UUID>
{
}
