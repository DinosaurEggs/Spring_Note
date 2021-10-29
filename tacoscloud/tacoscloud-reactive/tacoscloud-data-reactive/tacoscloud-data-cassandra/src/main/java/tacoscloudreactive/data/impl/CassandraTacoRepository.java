package tacoscloudreactive.data.impl;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import tacoscloudreactive.data.TacoRepository;
import tacoscloudreactive.domain.Taco;

import java.util.UUID;

//@Repository
//public class CassandraTacoRepository implements TacoRepository
//{
//    private final TacoRepository_ tacoRepository;
//
//    public CassandraTacoRepository(TacoRepository_ tacoRepository)
//    {
//        this.tacoRepository = tacoRepository;
//    }
//
//
//    @Override
//    public Mono<Taco> findById(String id)
//    {
//        return tacoRepository.findById(UUID.fromString(id));
//    }
//
//    @Override
//    public Mono<Taco> save(Taco taco)
//    {
//        return tacoRepository.save(taco);
//    }
//}

interface TacoRepository_ extends ReactiveCassandraRepository<Taco, UUID>
{}
