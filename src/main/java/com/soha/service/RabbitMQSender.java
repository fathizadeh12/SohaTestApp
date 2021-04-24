package com.soha.service;

import com.soha.controller.RabbitMQWebController;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.soha.dto.EmployeeRequest;

/**
 * Created by Ali Fathizadeh on 23/4/2021.
 */
@Service
public class RabbitMQSender {

    public final AmqpTemplate amqpTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingkey}")
    private String routingKey;

    private static final Logger LOGGER = LogManager.getLogger(RabbitMQWebController.class);

    public RabbitMQSender(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    // send message to the queue
    public void send(EmployeeRequest emp) {
        LOGGER.info("send(), input: " + emp.toString());

        amqpTemplate.convertAndSend(exchange, routingKey, emp);

        LOGGER.info("send() finished");

    }

}