package tacoscloudreactive.web;

import org.thymeleaf.spring5.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import tacoscloudreactive.OrderProps;
import tacoscloudreactive.data.OrderRepository;
import tacoscloudreactive.domain.Order;
import tacoscloudreactive.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.time.Duration;


@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
//@ConfigurationProperties(prefix = "taco.orders")//在配置文件中创建设置路径
public class OrderController
{
    private final OrderRepository orderRepository;
    private final OrderProps orderProps;

    public OrderController(OrderRepository orderRepository, OrderProps orderProps)
    {
        this.orderRepository = orderRepository;
        this.orderProps = orderProps;
    }

    @GetMapping("/current")
    public String orderForm(Model model)
    {

        Order order = (Order) model.getAttribute("order");
        if (order == null) {
            log.info("No Order");
            return "redirect:/error/access";
        } else if (order.getTacos().isEmpty()) {
            log.info("No Tacos");
            return "redirect:/error/access";
        }


        log.info("Now order is:\n" + order);
        return "orderForm";
    }

    @GetMapping
    public String ordersForUser(@AuthenticationPrincipal User user, Model model)
    {
        log.info("User \n" + user);
        Pageable pageable = PageRequest.of(0, this.orderProps.getPageSize());//数据分页

        IReactiveDataDriverContextVariable orders = new ReactiveDataDriverContextVariable(
                orderRepository.findByUserOrderByPlacedAtDesc(user, pageable)
                        .delayElements(Duration.ofMillis(500)),
                1
        );

        model.addAttribute("orders", orders);
        return "orderList";
    }

    @PostMapping
    public String processOrder(@Valid @ModelAttribute Order order, Errors errors, SessionStatus sessionStatus, @AuthenticationPrincipal User user)
    {
        if (errors.hasErrors()) {
            return "orderForm";
        }

        log.info("Order submitted: " + order);
        log.info("Tacos:" + order.getTacos());

        order.setUser(user);

        orderRepository.save(order).subscribe();
        log.info("Order added!\n" + order);
        sessionStatus.setComplete();

        return "redirect:/orders";
    }

}
