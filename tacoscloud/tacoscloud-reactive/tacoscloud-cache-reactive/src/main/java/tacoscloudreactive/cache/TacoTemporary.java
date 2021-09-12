package tacoscloudreactive.cache;

import reactor.core.publisher.Mono;
import tacoscloudreactive.domain.Taco;

public interface TacoTemporary
{
    Mono<Taco> findByName(String name, String userId);

    Mono<Boolean> save(Taco taco, String userId);
}
