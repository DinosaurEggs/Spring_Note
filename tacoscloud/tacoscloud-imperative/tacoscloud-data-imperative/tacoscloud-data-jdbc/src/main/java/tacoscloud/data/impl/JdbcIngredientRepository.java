package tacoscloud.data.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tacoscloud.data.IngredientRepository;
import tacoscloud.domain.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcIngredientRepository implements IngredientRepository
{
    JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcIngredientRepository(JdbcTemplate jdbc)
    {
        this.jdbcTemplate = jdbc;
    }

    public List<Ingredient> findAll()
    {
        return jdbcTemplate.query("select id, name, type from tacoscloud.ingredient", this::mapRowToIngredient);
    }


    public Optional<Ingredient> findById(String id)
    {
        return Optional.ofNullable(jdbcTemplate.queryForObject("select id, name, type from tacoscloud.ingredient where id=?", this::mapRowToIngredient, id));
    }

    @Override
    public List<Ingredient> findByTacoId(long tacoId)
    {
        String sql = "select name, type, id from ingredient where id=any(select ingredient_id from taco_ingredient where taco_id=?)";
        return jdbcTemplate.query(sql,
                (rs, i) -> new Ingredient(
                        rs.getString("id"),
                        rs.getString("name"),
                        Ingredient.Type.valueOf(rs.getString("type"))
                ),
                tacoId);
    }

    public Ingredient save(Ingredient ingredient)
    {
        jdbcTemplate.update("insert into tacoscloud.ingredient (id, name, type) values (?, ?, ?)",
                ingredient.getId(), ingredient.getName(), ingredient.getType().toString());
        return ingredient;
    }

    private Ingredient mapRowToIngredient(ResultSet rs, int rowNum) throws SQLException
    {
        return new Ingredient(rs.getString("id"), rs.getString("name"), Ingredient.Type.valueOf(rs.getString("type")));
    }
}
