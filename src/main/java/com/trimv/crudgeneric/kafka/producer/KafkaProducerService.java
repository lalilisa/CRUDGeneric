package com.trimv.crudgeneric.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public <T> void sendMessage(String topic, T data) {
        try {
            String message = objectMapper.writeValueAsString(data); // Convert object -> JSON string
            kafkaTemplate.send(topic, message);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public <T> void sendMessage(String topic, T data, String messageKey, Integer partition) {
        try {
            long timestamp = System.currentTimeMillis();
            String message = objectMapper.writeValueAsString(data);
            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, partition, timestamp, messageKey, message);

            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    RecordMetadata metadata = result.getRecordMetadata();
                    log.info("Sent message=[{}] to partition=[{}] with offset=[{}]", message, metadata.partition(), metadata.offset());
                } else {
                    log.error("Unable to send message=[{}] due to: {}", message, ex.getMessage());
                }
            });

        } catch (Exception e) {
            log.error("", e);
        }
    }
}
