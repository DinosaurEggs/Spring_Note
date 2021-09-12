package tacoscloud.kitchen.messaging.jms;

import tacoscloud.kitchen.KitchenUI;
import tacoscloud.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderListener
{
    private final KitchenUI ui;

    @Autowired
    public OrderListener(KitchenUI ui)
    {
        this.ui = ui;
    }

    @JmsListener(destination = "tacocloud.order.queue")
    public void receiveOrder(Order order)
    {
        ui.displayOrder(order);
    }
}
