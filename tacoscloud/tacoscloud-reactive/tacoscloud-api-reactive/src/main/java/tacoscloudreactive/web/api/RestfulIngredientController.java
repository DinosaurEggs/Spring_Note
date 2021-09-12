package tacoscloudreactive.web.api;


import reactor.core.publisher.Flux;
import tacoscloudreactive.data.IngredientRepository;
import tacoscloudreactive.domain.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/ingredient")
@CrossOrigin
public class RestfulIngredientController
{
    private final IngredientRepository ingredientRepository;

    @Autowired
    public RestfulIngredientController(IngredientRepository ingredientRepository)
    {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping
    public Flux<Ingredient> allIngredient()
    {
        return ingredientRepository.findAll();
    }
}
