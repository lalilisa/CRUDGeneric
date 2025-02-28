package com.trimv.crudgeneric.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trimv.crudgeneric.kafka.KafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class OrderConsumer extends BaseKafkaConsumer<KafkaMessage> {

    private final ObjectMapper objectMapper;

    public OrderConsumer(ObjectMapper objectMapper) {
        super("order-topic", List.of("order"), objectMapper);
        this.objectMapper = objectMapper;
    }


    @Override
    protected void processMessage(KafkaMessage message) {
        log.info("processMessage: {}", message);

        if (message == null) {
            log.error("Message is null -> return");
            return;
        }

        try {
            var req = this.objectMapper.readValue(this.objectMapper.writeValueAsBytes(message.getData()), KafkaMessage.Name.class);
            log.info("processReq: {}", req);

        } catch (IOException e) {
            log.error("Error while processing kafka message", e);
        }
    }

    @Override
    protected Class<KafkaMessage> getMessageClass() {
        return KafkaMessage.class;
    }
}