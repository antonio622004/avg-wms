package com.warehouse.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.warehouse.listener.OrderMessage;
import com.warehouse.listener.OrderMessage.Customer;
import com.warehouse.listener.OrderMessage.Order;

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
    public void orderShipped(OrderMessage orderMessage) {
        Order order = orderMessage.getOrder();
        Customer customer = order != null ? order.getCustomer() : null;
        String customerName = buildCustomerName(customer);
        String address = orderMessage.getShippingAddressAsString();

        String msg = "Order shipped to: " + customerName + " with address: " + address;

        rabbitTemplate.convertAndSend("status.queue", msg);
        logger.info("Sent status update: {}", msg);
    }

    private String buildCustomerName(Customer customer) {
        if (customer == null) {
            return "unknown customer";
        }
        String first = customer.getPrename() == null ? "" : customer.getPrename().trim();
        String last = customer.getName() == null ? "" : customer.getName().trim();
        String full = (first + " " + last).trim();
        return full.isEmpty() ? "unknown customer" : full;
    }
}
