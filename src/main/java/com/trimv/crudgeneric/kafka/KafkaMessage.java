package com.trimv.crudgeneric.kafka;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KafkaMessage<T> {

    private String event;
    private long producedAt;
    private T data;


    @Getter
    @Setter
    public static class Name {
        private String name;
    }
}
