package com.trimv.crudgeneric.controller;

import com.trimv.crudgeneric.domain.DynamicApi;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

public class DynamicApiController {

    private final DynamicApi api;

    private final KafkaTemplate<String, String> kafkaTemplate;
    public DynamicApiController(DynamicApi api, KafkaTemplate<String, String> kafkaTemplate) {
        this.api = api;
        this.kafkaTemplate = kafkaTemplate;
    }

    @ResponseBody
    public ResponseEntity<Object> handleRequest(Map<String,Object> params,@RequestBody(required = false) String requestBody) {
        // Optional: Validate requestBody using JSON Schema stored in requestSchema (if needed)
        Map<String,Object> responseBody = new HashMap<>();
        responseBody.put("status", 200);
        responseBody.put("message", "OK");
        return ResponseEntity.ok(api.getResponse());
    }


}

