package tacoscloud.data.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tacoscloud.data.IngredientRepository;
import tacoscloud.data.TacoRepository;
import tacoscloud.domain.Ingredient;
import tacoscloud.domain.Taco;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
public class JdbcTacoRepository implements TacoRepository
{

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert inserter;

    private final IngredientRepository ingredientRepository;

    public JdbcTacoRepository(JdbcTemplate jdbcTemplate, IngredientRepository ingredientRepository)
    {
        this.jdbcTemplate = jdbcTemplate;
        inserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("taco")
                .usingGeneratedKeyColumns("id");
        this.ingredientRepository = ingredientRepository;
    }


    @Override
    public Taco save(Taco taco)
    {
        //taco
        taco.setCreateDat();
        Map<String, Object> values = new HashMap<>();
        values.put("name", taco.getName());
        values.put("createdAt", new Timestamp(taco.getCreatedAt().getTime()));
        Long tacoId = inserter.executeAndReturnKey(values).longValue();
        taco.setId(tacoId);

/*      返回自增id至keyholder
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(
                            "insert into taco (id, name, createdat) values (SEQ_TACO_IDENTITY.nextval,?, ?)",
                            new String[]{"id"}
                    );

                    ps.setString(1, taco.getName());
                    ps.setTimestamp(2, new Timestamp(taco.getCreatedAt().getTime()));
                    return ps;
                },
                keyHolder
        );

        long tacoid = Objects.requireNonNull(keyHolder.getKey()).longValue()
*/

        //taco_ingredient
        for (Ingredient ingredient : taco.getIngredients())
            jdbcTemplate.update(
                    "insert into taco_ingredient (taco_id, ingredient_id) values (?, ?)",
                    tacoId, ingredient.getId()
            );

        return taco;
    }


    @Override
    @Transactional
    public void deleteById(Long id)
    {
        jdbcTemplate.update("delete from taco where id=?", id);
        jdbcTemplate.update("delete from taco_ingredient where taco_id=?", id);
    }

    @Override
    public List<Taco> findAll()
    {
        return jdbcTemplate.query("select createdat, name, id from taco", this::mapRowToTaco);
    }

    @Override
    public Page<Taco> findAll(Pageable pageable)
    {
        List<Taco> tacos = jdbcTemplate.query("select createdat, name, id from taco limit ?,?",
                this::mapRowToTaco,
                pageable.getPageNumber(),
                pageable.getPageSize()
        );
        return new PageImpl<>(tacos);
    }

    @Override
    public List<Taco> findByOrderId(Long orderId)
    {
        return jdbcTemplate.query("select createdat, name, id from taco where id=any(select taco_id from order_taco where order_id=?)", this::mapRowToTaco,orderId);
    }

    @Override
    public Optional<Taco> findById(Long id)
    {
        return Optional.ofNullable(jdbcTemplate.queryForObject("select createdat, name, id from taco where id=?", this::mapRowToTaco,id));
    }

    private Taco mapRowToTaco(ResultSet rs, int num) throws SQLException
    {
        return new Taco(
                rs.getLong("id"),
                rs.getDate("createdat"),
                rs.getString("name"),
                ingredientRepository.findByTacoId(rs.getLong("id"))
        );
    }
}