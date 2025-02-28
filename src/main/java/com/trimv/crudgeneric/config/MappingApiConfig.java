package com.trimv.crudgeneric.config;

import com.trimv.crudgeneric.controller.DynamicApiController;
import com.trimv.crudgeneric.domain.DynamicApi;
import com.trimv.crudgeneric.repository.ApiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;

@Configuration
@Slf4j
public class MappingApiConfig implements ApplicationListener<ContextRefreshedEvent> {

    private final ApiRepository repository;
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public MappingApiConfig(
            ApiRepository repository,
            RequestMappingHandlerMapping requestMappingHandlerMapping,
            KafkaTemplate<String, String> kafkaTemplate
            ) {
        this.repository = repository;
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<DynamicApi> apis = repository.findAll();
        log.info("Loaded API: {}", apis);
        apis.forEach(this::registerDynamicApi);
    }

    private void registerDynamicApi(DynamicApi api) {
        RequestMappingInfo mappingInfo = RequestMappingInfo
                .paths(api.getPath())
                .methods(RequestMethod.valueOf(api.getMethod()))
                .build();

        requestMappingHandlerMapping.registerMapping(mappingInfo, new DynamicApiController(api,kafkaTemplate),
                DynamicApiController.class.getMethods()[0]);

        log.info("Registered API: {}", api);
    }
}
