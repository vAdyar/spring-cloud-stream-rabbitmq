package com.application.ingestion;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface MessageBindings {

    String INPUT = "ingestion-command";
    String OUTPUT = "ingestion-event";

    @Input(INPUT)
    MessageChannel ingestionCommand();

    @Output(OUTPUT)
    SubscribableChannel ingestionEvent();
}
