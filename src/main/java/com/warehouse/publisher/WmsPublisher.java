package com.warehouse.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.warehouse.listener.OrderMessage;

@Component
public class WmsPublisher {

    private static final Logger logger = LoggerFactory.getLogger(WmsPublisher.class);
    private final RabbitTemplate rabbitTemplate;

    public WmsPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void itemsPicked(OrderMessage order) {
        String msg = "Items picked for Order " + order.getOrderId() +
                     ": " + order.getItemsList();

        rabbitTemplate.convertAndSend("status.queue", msg);
        logger.info("Sent status update: {}", msg);
    }

    public void orderPacked(OrderMessage order) {
        String msg = "Order packed: " + order.getOrderId();

        rabbitTemplate.convertAndSend("status.queue", msg);
        logger.info("Sent status update: {}", msg);
    }

    public void orderShipped(OrderMessage order) {
        String msg = "Order shipped to: " + order.getShippingAddressAsString();

        rabbitTemplate.convertAndSend("status.queue", msg);
        logger.info("Sent status update: {}", msg);
    }
}
