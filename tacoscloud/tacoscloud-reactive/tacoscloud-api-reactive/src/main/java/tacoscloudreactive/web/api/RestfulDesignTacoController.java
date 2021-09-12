package tacoscloudreactive.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tacoscloudreactive.data.TacoRepository;
import tacoscloudreactive.domain.Taco;

@RestController
@RequestMapping(path = "/api/design", produces = "application/json")
@CrossOrigin
public class RestfulDesignTacoController
{
    private final TacoRepository tacoRepository;

    @Autowired
    public RestfulDesignTacoController(TacoRepository tacoRepository)
    {
        this.tacoRepository = tacoRepository;
    }

    @GetMapping("/recent")
    public Flux<Taco> recentTacos()
    {
        return tacoRepository.findAll().take(12);
    }


    @GetMapping("/{id}")
    public Mono<Taco> tacoById(@PathVariable("id") String id)
    {
        return tacoRepository.findById(id);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Taco> postTaco(@RequestBody Taco taco)
    {
        return tacoRepository.save(taco);
    }
}
