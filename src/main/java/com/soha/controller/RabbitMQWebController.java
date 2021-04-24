package com.soha.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.soha.dto.EmployeeRequest;
import com.soha.service.RabbitMQSender;

/**
 * Created by Ali Fathizadeh on 23/4/2021.
 */
@RestController
@RequestMapping(value = "/api/")
public class RabbitMQWebController {
    private static final Logger LOGGER = LogManager.getLogger(RabbitMQWebController.class);
    final RabbitMQSender rabbitMQSender;

    public RabbitMQWebController(RabbitMQSender rabbitMQSender) {
        this.rabbitMQSender = rabbitMQSender;
    }

    @PostMapping(value = "/putmessage")
    public ResponseEntity<String> producer(@RequestBody EmployeeRequest emp) {
        LOGGER.info("called url: /api/putmessage");
        rabbitMQSender.send(emp);
        return new ResponseEntity<>("Message sent to the RabbitMQ Successfully", HttpStatus.OK);
    }

}

