package tacoscloud.web.api;

import tacoscloud.data.IngredientRepository;
import tacoscloud.domain.Ingredient;

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
    public Iterable<Ingredient> allIngredient()
    {
        return ingredientRepository.findAll();
    }
}
