package tacoscloud.data.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tacoscloud.data.OrderRepository;
import tacoscloud.data.TacoRepository;
import tacoscloud.data.UserRepository;
import tacoscloud.domain.Order;
import tacoscloud.domain.Taco;
import tacoscloud.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcOrderRepository implements OrderRepository
{
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert orderInserter;
    private final SimpleJdbcInsert orderTacoInserter;
    private final ObjectMapper objectMapper;

    private final UserRepository userRepository;
    private final TacoRepository tacoRepository;

    public JdbcOrderRepository(JdbcTemplate jdbc, UserRepository userRepository, TacoRepository tacoRepository)
    {
        this.jdbcTemplate = jdbc;

        this.orderInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("orders")
                .usingGeneratedKeyColumns("id");

        this.orderTacoInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("order_taco");

        this.userRepository = userRepository;
        this.tacoRepository = tacoRepository;

        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable)
    {
        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();
        String sql = "select id, userid, deliveryzip, deliverystate, deliverycity, ccexpiration, placedat, deliverystreet, deliveryname, ccnumber, cccvv from orders where userid=? order by placedat desc limit ?,?";
        return jdbcTemplate.query(sql, this::mapRowToOrder, user.getId(), pageNumber, pageSize);
        // -不可缺省- rowMapper:将返回值转换成类
    }

    @Override
    public Optional<Order> findById(Long id)
    {
        String sql = "select * from orders where id=?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, this::mapRowToOrder, id));
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public Order save(Order order)
    {
        order.setPlacedAt();

        Map<String, Object> values = objectMapper.convertValue(order, Map.class);
        values.put("placedat", order.getPlacedAt());
        values.put("userid", order.getUser().getId());
        long orderId = orderInserter.executeAndReturnKey(values).longValue();//orderid
        order.setId(orderId);

        List<Taco> tacos = order.getTacos();
        for (Taco taco : tacos) {
            taco = tacoRepository.save(taco);
            Map<String, Object> tacoOrderValues = new HashMap<>();
            tacoOrderValues.put("order_id", orderId);
            tacoOrderValues.put("taco_id", taco.getId());
            orderTacoInserter.execute(tacoOrderValues);//保存中间表
        }
        return order;
    }

    @Override
    @Transactional
    public void deleteById(Long id)
    {
        List<Long> tacos_id = jdbcTemplate.query("select taco_id from order_taco where order_id=?", (rs, var) -> rs.getLong("taco_id"),id);
        for (long taco_id : tacos_id) {
            tacoRepository.deleteById(taco_id);
        }
        jdbcTemplate.update("delete from order_taco where order_id=?", id);
        jdbcTemplate.update("delete from orders where id=?", id);
    }

    private Order mapRowToOrder(ResultSet rs, int rowNum) throws SQLException
    {
        User user = userRepository.findById(rs.getLong("userid"));
        List<Taco> tacos = tacoRepository.findByOrderId(rs.getLong("id"));
        return new Order(
                rs.getLong("id"),
                user,
                rs.getDate("placedat"),
                rs.getString("deliveryname"),
                rs.getString("deliverystreet"),
                rs.getString("deliverycity"),
                rs.getString("deliverystate"),
                rs.getString("deliveryzip"),
                rs.getString("ccnumber"),
                rs.getString("ccexpiration"),
                rs.getString("cccvv"),
                tacos
        );
    }
}
