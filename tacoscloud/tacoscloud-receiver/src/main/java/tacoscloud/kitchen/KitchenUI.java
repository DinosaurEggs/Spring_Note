package tacoscloud.kitchen;

import tacoscloud.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KitchenUI
{
    public void displayOrder(Order order)
    {
        log.info("Reveive Order:" + order);
    }
}
