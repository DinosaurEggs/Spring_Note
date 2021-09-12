package tacoscloud.messaging;

import tacoscloud.domain.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaOrderMessageService implements OrderMessagingService
{
    private final KafkaTemplate<String, Order> kafkaTemplate;

    public KafkaOrderMessageService(KafkaTemplate<String, Order> kafkaTemplate)
    {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendOrder(Order order)
    {
        kafkaTemplate.sendDefault(order);

        kafkaTemplate.send("tacocloud.orders.topic", order);
    }
}
