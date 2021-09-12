package tacoscloud.kitchen.messaging.rabbit;

import tacoscloud.kitchen.OrderReceiver;
import tacoscloud.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitOrderReceiver implements OrderReceiver
{
    private final RabbitTemplate rabbitTemplate;
    private final MessageConverter converter;

    public RabbitOrderReceiver(RabbitTemplate rabbitTemplate)
    {
        this.rabbitTemplate = rabbitTemplate;
        this.converter = rabbitTemplate.getMessageConverter();
    }

    @Override
    public Order receiveOrder()
    {
        @SuppressWarnings("UnusedAssignment")
        Order order = null;

        Message message = rabbitTemplate.receive("tacocloud.order.queue");
        order = message != null ? (Order) converter.fromMessage(message) : null;

//        order = (Order) rabbitTemplate.receiveAndConvert("tacocloud.order.queue");
        return order;
    }
}
