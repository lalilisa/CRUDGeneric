package com.trimv.crudgeneric.model.res;

import lombok.*;

@Setter
@ToString
@Getter
public class ApiResponse<T> {

    private String status;
    private T data;
    private String message;

}
