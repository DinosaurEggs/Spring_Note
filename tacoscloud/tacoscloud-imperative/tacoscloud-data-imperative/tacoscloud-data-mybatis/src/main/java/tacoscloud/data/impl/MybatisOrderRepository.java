package tacoscloud.data.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tacoscloud.data.OrderRepository;
import tacoscloud.data.TacoRepository;
import tacoscloud.domain.Order;
import tacoscloud.domain.Taco;
import tacoscloud.domain.User;
import tacoscloud.mapper.OrderMapper;

import java.util.List;
import java.util.Optional;

@Repository
public class MybatisOrderRepository implements OrderRepository
{
    private final OrderMapper orderMapper;
    private final TacoRepository tacoRepository;

    public MybatisOrderRepository(OrderMapper orderMapper, TacoRepository tacoRepository)
    {
        this.orderMapper = orderMapper;
        this.tacoRepository = tacoRepository;
    }

    @Override
    public List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable)
    {
        return orderMapper.findByUserOrderByPlacedAtDesc(user.getId(), pageable.getPageNumber(), pageable.getPageSize());
    }

    @Override
    public Optional<Order> findById(Long id)
    {
        return orderMapper.findById(id);
    }

    @Override
    @Transactional
    public Order save(Order order)
    {
        orderMapper.save(order);
        long orderId = order.getId();
        for (Taco taco : order.getTacos()) {
            tacoRepository.save(taco);
            orderMapper.saveOrderTaco(orderId, taco.getId());
        }
        return order;
    }

    @Override
    @Transactional
    public void deleteById(Long id)
    {
        orderMapper.deleteById(id);
    }
}
