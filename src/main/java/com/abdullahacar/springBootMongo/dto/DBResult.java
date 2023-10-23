package com.abdullahacar.springBootMongo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DBResult<T> {

    private DBResultCode code;
    private String jsonData;
    private String message;
    private T entity;
    private long count;
    private byte[] bytes;

}
