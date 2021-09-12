package tacoscloud.data.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tacoscloud.data.IngredientRepository;
import tacoscloud.domain.Ingredient;
import tacoscloud.mapper.IngredientMapper;

import java.util.List;
import java.util.Optional;

@Repository
public class MybatisIngredientRepository implements IngredientRepository
{
    private final IngredientMapper ingredientMapper;

    public MybatisIngredientRepository(IngredientMapper ingredientMapper)
    {
        this.ingredientMapper = ingredientMapper;
    }

    @Override
    public List<Ingredient> findAll()
    {
        return ingredientMapper.findAll();
    }

    @Override
    public Optional<Ingredient> findById(String id)
    {
        return ingredientMapper.findById(id);
    }

    @Override
    @Transactional
    public Ingredient save(Ingredient ingredient)
    {
        ingredientMapper.save(ingredient);
        return ingredient;
    }
}
