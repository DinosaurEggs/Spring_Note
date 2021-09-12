package tacoscloud.messaging;

import tacoscloud.domain.Order;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Service;

@Service
public class RabbitOrderMessagingService implements OrderMessagingService
{
    private final RabbitTemplate rabbitTemplate;

    public RabbitOrderMessagingService(RabbitTemplate rabbitTemplate)
    {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendOrder(Order order)
    {
        // -----发送原始消息-----
        // 调用 send() 之前，需要将 Order 对象转换为消息。
        // 默认情况下，使用 SimpleMessageConverter 执行消息转换，SimpleMessageConverter 能够将简单类型（如 String）和可序列化对象转换为消息对象。
        //  Jackson2JsonMessageConverter —— 使用Jackson 2 JSON处理器将对象与 JSON 进行转换
        //  MarshallingMessageConverter —— 使用 Spring 的序列化和反序列化抽象转换 String 和任何类型的本地对象
        //  SimpleMessageConverter —— 转换 String、字节数组和序列化类型
        //  ContentTypeDelegatingMessageConverter —— 基于 contentType 头信息将对象委托给另一个 MessageConverter
        //  MessagingMessageConverter —— 将消息转换委托给底层 MessageConverter，将消息头委托给 AmqpHeaderConverte
        MessageConverter messageConverter = rabbitTemplate.getMessageConverter();
        // 必须使用 MessageProperties 提供任何消息属性，但是如果不需要设置任何此类属性，则可以缺省实例。
        MessageProperties messageProperties = new MessageProperties();
        // 设置消息属性
        messageProperties.setHeader("X_ORDER_SOURCE", "WEB");
        Message message = messageConverter.toMessage(order, messageProperties);
        // 它们接受 String 值来指定交换和路由键(可选)。不接受交换的方法将把它们的消息发送到默认交换。不接受路由键的方法将使用默认路由键路由其消息。
        // 可以通过设置 spring.rabbitmq.template.exchange 和 spring.rabbitmq.template.routing-key 属性来覆盖这些缺省值。默认为""（空String）
        rabbitTemplate.send("tacocloud.order.queue", message);

        // -----发送从对象转换过来的消息-----
        rabbitTemplate.convertAndSend("tacocloud.order.queue", order);

        // -----发送经过处理后从对象转换过来的消息-----
        // noinspection Convert2Lambda
        rabbitTemplate.convertAndSend("tacocloud.order.queue", order,
                new MessagePostProcessor()
                {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException
                    {
                        MessageProperties messageProperties = message.getMessageProperties();
                        messageProperties.setHeader("X_ORDER_SOURCE", "WEB");
                        messageProperties.setType("");
                        return message;
                    }
                }
        );
    }
}
