package tacoscloud.web.api;

import tacoscloud.data.OrderRepository;
import tacoscloud.messaging.OrderMessagingService;
import tacoscloud.domain.Order;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/order")
@CrossOrigin
public class RestfulOrderController
{
    private final OrderRepository orderRepository;
    private final OrderMessagingService orderMessagingService;

    public RestfulOrderController(OrderRepository orderRepository, @Qualifier("rabbitOrderMessagingService") OrderMessagingService orderMessagingService)
    {
        this.orderMessagingService = orderMessagingService;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable("id") long id)
    {
        return orderRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Order putOrder(@RequestBody Order order)
    {
        return orderRepository.save(order);
    }


    @PatchMapping(value = "/{id}", consumes = "application/json")
    public Order patchOrder(@PathVariable("id") long orderId, @RequestBody Order patch)
    {
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order != null) {
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
                order.setDeliveryZip(patch.getDeliveryState());
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
            return orderRepository.save(order);
        }

        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable("id") long id)
    {
        try {
            orderRepository.deleteById(id);

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
    }

    @PostMapping(value = "/sendorder", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Order postOrder(@RequestBody Order order)
    {
        orderMessagingService.sendOrder(order);
        return order;
    }
}
