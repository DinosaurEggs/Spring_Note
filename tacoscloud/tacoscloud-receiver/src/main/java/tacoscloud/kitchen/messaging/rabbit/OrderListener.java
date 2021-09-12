package tacoscloud.kitchen.messaging.rabbit;

import tacoscloud.kitchen.KitchenUI;
import tacoscloud.domain.Order;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

//@Component
public class OrderListener
{
    private final KitchenUI ui;

    public OrderListener(KitchenUI ui)
    {
        this.ui = ui;
    }

    @RabbitListener(queues = "tacocloud.order.queue")
    public void receiveListener(Order order)
    {
        ui.displayOrder(order);
    }
}
