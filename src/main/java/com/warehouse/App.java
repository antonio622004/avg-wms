package com.warehouse;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.amqp.core.Queue;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        System.out.println("WMS starting and ready for RabbitMQ Messages");
    }

    @Bean
    public Queue ordersQueue() {
        return new Queue("orders.queue", true);
    }

    @Bean
    public Queue statusQueue() {
        return new Queue("status.queue", true); // true = durable
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
