package tacoscloud.data.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tacoscloud.data.TacoRepository;
import tacoscloud.domain.Ingredient;
import tacoscloud.domain.Taco;
import tacoscloud.mapper.TacoMapper;

import java.util.List;
import java.util.Optional;

@Repository
public class MybatisTacoRepository implements TacoRepository
{
    private final TacoMapper tacoMapper;

    public MybatisTacoRepository(TacoMapper tacoMapper)
    {
        this.tacoMapper = tacoMapper;
    }

    @Override
    public List<Taco> findAll()
    {
        return tacoMapper.findAll();
    }

    @Override
    public Page<Taco> findAll(Pageable pageable)
    {
        List<Taco> tacos = tacoMapper.findAllPaged(pageable.getPageNumber(), pageable.getPageSize());
        return new PageImpl<>(tacos);
    }

    @Override
    public Optional<Taco> findById(Long id)
    {
        return tacoMapper.findById(id);
    }

    @Override
    @Transactional
    public Taco save(Taco taco)
    {
        tacoMapper.save(taco);
        for (Ingredient ingredient : taco.getIngredients())
            tacoMapper.saveTacoIngredient(taco.getId(), ingredient.getId());

        return taco;
    }

    @Override
    @Transactional
    public void deleteById(Long id)
    {
        tacoMapper.deleteById(id);
    }
}
