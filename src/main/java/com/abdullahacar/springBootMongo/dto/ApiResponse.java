package com.abdullahacar.springBootMongo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {

    private T entity;
    private String entityJson;
    private byte[] bytes;
    private ApiResponseCode apiResponseCode;
    private long count;
    private String exception;
    private String message;

}
