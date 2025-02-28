package com.trimv.crudgeneric.kafka.consumer;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

import java.util.Date;
import java.util.List;


@Getter
public abstract class BaseKafkaConsumer<T> {
    private static final Logger logger = LoggerFactory.getLogger(BaseKafkaConsumer.class);

    private final String groupId;
    private final List<String> topic;
    private final ObjectMapper objectMapper;
    private final int retryTimes;

    public BaseKafkaConsumer(String groupId, List<String> topic, ObjectMapper objectMapper) {
        this.groupId = groupId;
        this.topic = topic;
        this.objectMapper = objectMapper;
        this.retryTimes = 1;
    }

    public BaseKafkaConsumer(String groupId, List<String> topic, ObjectMapper objectMapper, int retryTimes) {
        this.groupId = groupId;
        this.topic = topic;
        this.objectMapper = objectMapper;
        this.retryTimes = retryTimes;
    }

    protected abstract void processMessage(T message);

    protected abstract Class<T> getMessageClass();


    @KafkaListener(
            topics = "#{__listener.topic}",
            groupId = "#{__listener.groupId}"
    )
    @RetryableTopic(attempts = "#{__listener.retryTimes}")
    public void listen(
            @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
            @Header(value = KafkaHeaders.TIMESTAMP, required = false) Long producedAt, // need add config server.properties kafka
            @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long consumedAt,
            @Header(KafkaHeaders.OFFSET) Long offset,
            @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) String messageKey,
            String data
    ) {
        try {

            logger.info("Received message from topic {}, partition: {}, offset: {}, consumer-group: {} : {}, messageKey : {} (producedAt: {}, consumedAt: {}) ",
                    this.topic, partition, offset, this.groupId, data, messageKey, producedAt == null ? "" : new Date(producedAt), new Date(consumedAt));
            T payload = this.objectMapper.readValue(data, this.getMessageClass());
            processMessage(payload);
        } catch (Exception e) {
            logger.error("Error processing message from topic {}: {}", topic, data, e);
        }
    }

}