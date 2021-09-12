package tacoscloud.messaging;

import tacoscloud.domain.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

import java.util.HashMap;
import java.util.Map;

//@Configuration
public class MessagingConfig
{
    /*
     * 消息转换器
     * 除了把Java对象转换成对应的Jms Message之外还可以把Jms Message转换成对应的Java对象
     * MappingJackson2MessageConverter 使用Jackson 2 JSON库对消息进行与JSON的转换
     * MarshallingMessageConverter 使用 JAXB 对消息进行与 XML 的转换
     * MessagingMessageConverter 使用底层MessageConverter（用于有效负载）和JmsHeaderMapper（用于将Jms信息头映射到标准消息标头）将Message从消息传递抽象转换为Message，并从Message转换为Message
     * SimpleMessageConverter 默认的消息转换器，要求发送的对象实现Serializable接口。将String转换为TextMessage，将字节数组转换为BytesMessage，将Map转换为MapMessage，将Serializable转换为ObjectMessage
     */
    @Bean
    public MappingJackson2MessageConverter messageConverter()
    {
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        //设置发送到队列中的typeId的名称 它使接收者知道要将传入消息转换成什么类型。默认情况下，它将包含被转换类型的完全限定类名，要求接收方也具有相同的类型，具有相同的完全限定类名
        messageConverter.setTypeIdPropertyName("_typeId");

        Map<String, Class<?>> typeIdMappings = new HashMap<>();
        //Key对应typeId，Value为序列化的类
        typeIdMappings.put("order", Order.class);
        messageConverter.setTypeIdMappings(typeIdMappings);

        return messageConverter;
    }
}
