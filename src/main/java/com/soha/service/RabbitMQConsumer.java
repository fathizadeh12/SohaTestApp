package com.soha.service;

import com.soha.dto.EmployeeRequest;
import com.soha.repository.EmployeeRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Created by Ali Fathizadeh on 23/4/2021.
 */
@Component
public class RabbitMQConsumer {
    final EmployeeRepository employeeRepository;
    final RabbitMQSender rabbitMQSender;
    private static final Logger LOGGER = LogManager.getLogger(RabbitMQConsumer.class);


    public RabbitMQConsumer(EmployeeRepository employeeRepository, RabbitMQSender rabbitMQSender) {
        this.employeeRepository = employeeRepository;
        this.rabbitMQSender = rabbitMQSender;
    }


// listen to the rabbitmq.queue
    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receivedMessage(EmployeeRequest emp) {
        LOGGER.info("receivedMessage(), input: " + emp.toString());
        try {
            com.soha.domain.Employee employee = new com.soha.domain.Employee(emp.getEmpName(), emp.getEmpId());

//           save received message(employee object)
            employeeRepository.save(employee);

            LOGGER.info("saved object: " + emp.toString());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            LOGGER.error(e.getStackTrace());

//            requeue if can not save in database
            rabbitMQSender.send(emp);

            LOGGER.info("requeue object: " + emp.toString());
        }


    }
}