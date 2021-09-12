package tacoscloud.kitchen.messaging.kafka;

import tacoscloud.kitchen.KitchenUI;
import tacoscloud.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

//@Component
@Slf4j
public class OrderListener
{
    private final KitchenUI ui;

    public OrderListener(KitchenUI ui)
    {
        this.ui = ui;
    }

    @KafkaListener(topics = "tacocloud.orders.topic")
    public void receiveOrder(Order order)
    {
        ui.displayOrder(order);
    }

    public void receiveOrder(Order order, ConsumerRecord<String, Order> record)
    {
        log.info("Received from partition {} with timestamp {}", record.partition(), record.timestamp());
        ui.displayOrder(order);
    }

    public void handle(Order order, Message<Order> message)
    {
        MessageHeaders headers = message.getHeaders();
        log.info("Received from partition {} with timestamp {}", headers.get(KafkaHeaders.RECEIVED_PARTITION_ID), headers.get(KafkaHeaders.RECEIVED_TIMESTAMP));
        ui.displayOrder(order);
    }
}
