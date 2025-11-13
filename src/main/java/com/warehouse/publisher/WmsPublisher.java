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

    private static final String SERVICE_NAME = "WMS";

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

    public void publishLog(String pattern, Object... args) {
        String formatted = format(pattern, args);

        String msg = "[" + SERVICE_NAME + "] " + formatted;

        logger.info(msg);

        rabbitTemplate.convertAndSend("log.queue", msg);
    }


    private String format(String pattern, Object... args) {
        if (args == null || args.length == 0) return pattern;

        StringBuilder sb = new StringBuilder();
        int index = 0;

        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == '{' && i + 1 < pattern.length() && pattern.charAt(i + 1) == '}') {
                if (index < args.length) {
                    sb.append(args[index++]);
                } else {
                    sb.append("{?}");
                }
                i++;
            } else {
                sb.append(pattern.charAt(i));
            }
        }

        return sb.toString();
    }
}
