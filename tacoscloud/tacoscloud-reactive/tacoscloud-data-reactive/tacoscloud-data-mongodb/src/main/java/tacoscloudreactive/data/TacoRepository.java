package tacoscloudreactive.data;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import tacoscloudreactive.domain.Taco;

public interface TacoRepository extends ReactiveMongoRepository<Taco, String>
{
}
