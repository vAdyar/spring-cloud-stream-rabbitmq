package com.application.ingestion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@EnableBinding(MessageBindings.class)
@Slf4j
public class IngestionController {

    @Autowired
    MessageBindings messageBindings;

    @StreamListener(MessageBindings.INPUT)
//    @SendTo(MessageBindings.OUTPUT)
    public void ingestionCommand(Message<IngestionEntity> message) throws InterruptedException {
        log.info("Ingestion command received: " +message);
        IngestionEntity event = message.getPayload();
        if (!"movie.event".equals(message.getHeaders().get("routeTo"))) {

            Thread.currentThread().sleep(5000l);
            event.setS3Location("s3://updated-location/movie");
            messageBindings
                    .ingestionEvent()
                    .send(MessageBuilder
                            .withPayload(event)
                            .setHeader("routeTo", "movie.event")
                            .build()
                    );
            log.info("Movie ingested: " + event);
        }
//        return event;
    }

    @StreamListener(MessageBindings.OUTPUT)
    public void movieEventListner(IngestionEntity payload) {
        log.info("Ingestion event received: " +payload);
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID());
    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class IngestionEntity {

    UUID id;
    String s3Location;

}
