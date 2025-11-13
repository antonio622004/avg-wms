package com.warehouse.service;

import org.springframework.stereotype.Service;
import com.warehouse.listener.OrderMessage;
import com.warehouse.publisher.WmsPublisher;

@Service
public class WmsService {

    private final WmsPublisher publisher;

    public WmsService(WmsPublisher publisher) {
        this.publisher = publisher;
    }

    public void processOrder(OrderMessage order) {
        delay();

        publisher.itemsPicked(order);
        delay();

        publisher.orderPacked(order);
        delay();

        publisher.orderShipped(order);
    }

    // Delay von 5 Sekunden
    private void delay() {
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void sendLog(String pattern, Object... args) {
        publisher.publishLog(pattern, args);
    }
}
