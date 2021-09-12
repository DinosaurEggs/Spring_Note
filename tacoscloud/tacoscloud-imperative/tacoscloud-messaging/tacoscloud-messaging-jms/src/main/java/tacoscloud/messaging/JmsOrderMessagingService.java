package tacoscloud.messaging;

import tacoscloud.domain.Order;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

//@Service
public class JmsOrderMessagingService implements OrderMessagingService
{
    private final JmsTemplate jmsTemplate;

    public JmsOrderMessagingService(JmsTemplate jmsTemplate, MessageConverter messageConverter)
    {
        this.jmsTemplate = jmsTemplate;
        jmsTemplate.setMessageConverter(messageConverter);
    }


    @Override
    public void sendOrder(Order order)
    {
//        //----发送原始消息---- 需要一个 MessageCreator 来制造一个 Message 对象。
//        // 使用默认的目的地名称
//        // 传递 MessageCreator 的匿名内部类实现
//        // 转换为消息(Message) 是通过 MessageConverter 实现的
//        // noinspection Convert2Lambda
//        jmsTemplate.send(new MessageCreator()
//        {
//            @Override
//            public Message createMessage(Session session)
//                    throws JMSException
//            {
//                return session.createObjectMessage(order);
//            }
//        });
//        jmsTemplate.send(session -> session.createObjectMessage(order));
//        // 指定目的地
//        jmsTemplate.send(this.orderQueue(), session -> session.createObjectMessage(order));
//        jmsTemplate.send("tacocloud.order.queue", session -> session.createObjectMessage(order));
//
//        jmsTemplate.send("tacocloud.order.queue", session -> {
//                    Message message = session.createObjectMessage(order);
//                    message.setStringProperty("X_OORDER_SOURCE", "WEB");
//                    return message;
//                });
//
//        //-----发送转换自对象的消息----- 方法接受一个 Object，并在后台自动将该 Object 转换为一条 Message
//        // 不需要提供 MessageCreator 在发送之前会将该对象转换为消息(Message)
//        jmsTemplate.convertAndSend("tacocloud.order.queue", order);
//
//        //-----发送经过处理后从对象转换而来的消息----- 自动将一个 Object 转换成一条 Message，也会接受一个 MessagePostProcessor，以便在 Message 发送前对其进行定制
//        // 在发送消息之前调整在幕后创建的 Message
//        jmsTemplate.convertAndSend("tacocloud.order.queue", order, message -> {
//            message.setStringProperty("X_ORDER_SOURCE", "WEB");
//            return message;
//        });
        jmsTemplate.convertAndSend("tacocloud.order.queue", order, this::addOrderSource);
    }

    private Message addOrderSource(Message message) throws JMSException
    {
        message.setStringProperty("X_OORDER_SOURCE", "WEB");
        return message;
    }

    private Destination orderQueue()
    {
        return new ActiveMQQueue("tacocloud.order.queue");
    }
}
