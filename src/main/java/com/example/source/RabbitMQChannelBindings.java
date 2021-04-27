package com.example.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface RabbitMQChannelBindings {

	@Output("messageRegistrationChannel")
	MessageChannel messageRegistration();

}
