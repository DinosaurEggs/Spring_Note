package tacoscloud.kitchen;

import tacoscloud.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/orders")
public class OrderReceiverController
{
    private final OrderReceiver orderReceiver;

    @Autowired
    public OrderReceiverController(@Qualifier("rabbitOrderReceiver") OrderReceiver orderReceiver)
    {
        this.orderReceiver = orderReceiver;
    }

    @GetMapping("/receive")
    public String receiveOrder(Model model)
    {
        Order order = orderReceiver.receiveOrder();

        if (order != null) {
            model.addAttribute("order", order);
            return "receiveOrder";
        }

        return "noOrder";
    }
}
