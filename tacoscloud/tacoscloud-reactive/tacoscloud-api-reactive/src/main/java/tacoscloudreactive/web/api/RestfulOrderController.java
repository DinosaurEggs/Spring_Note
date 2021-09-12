package tacoscloudreactive.web.api;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import tacoscloudreactive.data.OrderRepository;
import tacoscloudreactive.domain.Order;


@Slf4j
@RestController
@RequestMapping("/api/order")
@CrossOrigin
public class RestfulOrderController
{
    private final OrderRepository orderRepository;

    public RestfulOrderController(OrderRepository orderRepository)
    {
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public Flux<Order> getAll()
    {
        return orderRepository.findAll()
                .switchIfEmpty(Flux.error(new UsernameNotFoundException("Order Not Found")))
                .delaySubscription(Duration.ofMillis(100))
                .delayElements(Duration.ofMillis(100))
                .map(order -> {
                    log.info("sended\n" + order);
                    return order;
                });
    }

    @GetMapping("/{id}")
    public Mono<Order> getOrder(@PathVariable("id") String id)
    {
        return orderRepository.findById(id);
    }

    @PutMapping("/{id}")
    public Mono<Order> putOrder(@RequestBody Order order)
    {
        return orderRepository.save(order);
    }

    @PatchMapping(value = "/{id}", consumes = "application/json")
    public Mono<Order> patchOrder(@PathVariable("id") String orderId, @RequestBody Order patch)
    {
        return orderRepository.findById(orderId)
                .map(order -> {
                    if (patch.getDeliveryName() != null) {
                        order.setDeliveryName(patch.getDeliveryName());
                    }
                    if (patch.getDeliveryStreet() != null) {
                        order.setDeliveryStreet(patch.getDeliveryStreet());
                    }
                    if (patch.getDeliveryCity() != null) {
                        order.setDeliveryCity(patch.getDeliveryCity());
                    }
                    if (patch.getDeliveryState() != null) {
                        order.setDeliveryState(patch.getDeliveryState());
                    }
                    if (patch.getDeliveryZip() != null) {
                        order.setDeliveryZip(patch.getDeliveryZip());
                    }
                    if (patch.getCcNumber() != null) {
                        order.setCcNumber(patch.getCcNumber());
                    }
                    if (patch.getCcExpiration() != null) {
                        order.setCcExpiration(patch.getCcExpiration());
                    }
                    if (patch.getCcCVV() != null) {
                        order.setCcCVV(patch.getCcCVV());
                    }
                    return order;
                })
                .flatMap(orderRepository::save);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable("id") String id)
    {
        try {
            orderRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
    }
}
