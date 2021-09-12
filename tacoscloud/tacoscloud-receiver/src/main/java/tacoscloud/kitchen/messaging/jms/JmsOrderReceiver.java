package tacoscloud.kitchen.messaging.jms;

import tacoscloud.kitchen.OrderReceiver;
import tacoscloud.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JmsOrderReceiver implements OrderReceiver
{
    private final JmsTemplate jmsTemplate;

    @Autowired
    public JmsOrderReceiver(JmsTemplate jmsTemplate)
    {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public Order receiveOrder()
    {
        return (Order) jmsTemplate.receiveAndConvert("tacocloud.order.queue");
    }
}
