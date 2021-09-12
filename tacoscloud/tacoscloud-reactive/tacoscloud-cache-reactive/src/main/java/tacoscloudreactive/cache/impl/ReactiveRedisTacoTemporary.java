package tacoscloudreactive.cache.impl;

import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import tacoscloudreactive.cache.TacoTemporary;
import tacoscloudreactive.domain.Taco;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReactiveRedisTacoTemporary implements TacoTemporary
{
    private final ReactiveHashOperations<String, Object, Object> hashOperations;

    public ReactiveRedisTacoTemporary(ReactiveRedisTemplate<String, Object> redisTemplate)
    {
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public Mono<Taco> findByName(String name, String userId)
    {
        List<Object> paramObjects = new ArrayList<>();
        paramObjects.add("name");
        paramObjects.add("date");
        return hashOperations.multiGet(userId + "-" + name, paramObjects).map(
                objects -> {
                    Taco taco = new Taco();
                    taco.setName(objects.get(0).toString());
                    taco.setCreatedAt(new Date(Long.parseLong((String) objects.get(1))));
                    return taco;
                }
        );
    }

    @Override
    public Mono<Boolean> save(Taco taco, String userId)
    {
        return hashOperations.putAll(userId + "-" + taco.getName(), mapForTaco(taco));
    }

    private Map<Object, Object> mapForTaco(Taco taco)
    {
        Map<Object, Object> tacoMap = new HashMap<>();
        tacoMap.put("name", taco.getName());
        tacoMap.put("date", taco.getCreatedAt().getTime()+"");
        return tacoMap;
    }
}
