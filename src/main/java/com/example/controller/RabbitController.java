package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import com.example.model.Payload;
import com.example.source.RabbitMQChannelBindings;

@RestController
@EnableBinding({RabbitMQChannelBindings.class, Sink.class})
public class RabbitController {

	@Autowired
    RabbitMQChannelBindings rabbitMQChannelBindings;

	@PostMapping("/register")
	public String postMessageTo(@RequestBody Payload payload) {
		rabbitMQChannelBindings.messageRegistration().send(MessageBuilder.withPayload(payload).build());
		System.out.println(payload.toString());
		return "Message Delivered";
	}

    @StreamListener(target = Sink.INPUT)
    public void processRegisterEmployees(String payload) {
        System.out.println("Message received by Client " + payload);
    }
}
