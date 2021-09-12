package tacoscloud.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tacoscloud.data.TacoRepository;
import tacoscloud.domain.Taco;

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
    public Iterable<Taco> recentTacos()
    {
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        return tacoRepository.findAll(page).getContent();
    }


    @GetMapping("/{id}")
    public Taco tacoById(@PathVariable("id") long id)
    {

        return tacoRepository.findById(id).orElse(null);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco postTaco(@RequestBody Taco taco)
    {
        return tacoRepository.save(taco);
    }
}
