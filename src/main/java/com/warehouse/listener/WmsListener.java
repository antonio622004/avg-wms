package com.warehouse.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.warehouse.service.WmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class WmsListener {

    private static final Logger logger = LoggerFactory.getLogger(WmsListener.class);
    private final WmsService wmsService;
    private final ObjectMapper objectMapper;

    public WmsListener(WmsService wmsService, ObjectMapper objectMapper) {
        this.wmsService = wmsService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "orders.queue")
    public void receiveOrder(String payload) {
        OrderMessage orderMessage = convertPayload(payload);
        logger.info("Order arrived at Warehouse with Order ID: {}", orderMessage.getOrderId());
        wmsService.processOrder(orderMessage);
    }

    private OrderMessage convertPayload(String payload) {
        try {
            return objectMapper.readValue(payload, OrderMessage.class);
        } catch (Exception ex) {
            logger.error("Failed to deserialize incoming order payload: {}", payload, ex);
            throw new IllegalArgumentException("Invalid order payload", ex);
        }
    }
}
