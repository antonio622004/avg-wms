package com.warehouse.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.warehouse.service.WmsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class WmsListener {

    private static final Logger logger = LoggerFactory.getLogger(WmsListener.class);
    private final WmsService wmsService;

    public WmsListener(WmsService wmsService) {
        this.wmsService = wmsService;
    }

    @RabbitListener(queues = "orders.queue")
    public void receiveOrder(OrderMessage orderMessage) {
        logger.info("Order arrived at Warehouse with Order ID: {}", 
            orderMessage.getOrderId()
        );

        wmsService.processOrder(orderMessage);
    }
}
