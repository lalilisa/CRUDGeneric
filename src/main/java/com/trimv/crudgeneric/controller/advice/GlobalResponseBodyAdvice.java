package com.trimv.crudgeneric.controller.advice;

import com.trimv.crudgeneric.model.res.ApiResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
//
//@ControllerAdvice
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // Wrap or modify response body here
        if (body instanceof ApiResponse) {
            // Modify the response as needed
            return body;
        }

        var res = new ApiResponse<>();
        res.setData(body);
        res.setStatus("SUCCESS");
        res.setMessage("SUCCESS");
        return res;
    }
}
