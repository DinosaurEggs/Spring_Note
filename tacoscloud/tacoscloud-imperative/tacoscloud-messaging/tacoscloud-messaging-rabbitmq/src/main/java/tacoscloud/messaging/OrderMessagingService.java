package tacoscloud.messaging;

import tacoscloud.domain.Order;

public interface OrderMessagingService
{
    void sendOrder(Order order);
}
