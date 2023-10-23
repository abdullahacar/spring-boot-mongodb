package com.abdullahacar.springBootMongo.service;

import com.abdullahacar.springBootMongo.dto.ApiResponse;
import com.abdullahacar.springBootMongo.dto.ApiResponseCode;
import com.abdullahacar.springBootMongo.dto.DBResult;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class ApiResponseGenerator<T> {


    public static <T> ApiResponse<T> ok(DBResult<T> result) {
        return ApiResponse.<T>builder()
                .apiResponseCode(ApiResponseCode.SUCCESSFUL)
                .entity(result.getEntity())
                .bytes(result.getBytes())
                .count(result.getCount())
                .message(result.getMessage())
                .build();

    }

    public static <T> ApiResponse<T> databaseWarning(String message) {
        return ApiResponse.<T>builder()
                .apiResponseCode(ApiResponseCode.WARNING)
                .entity(null)
                .message(message)
                .count(0)
                .build();
    }

    public static <T> ApiResponse<T> databaseError(String message) {
        return ApiResponse.<T>builder()
                .apiResponseCode(ApiResponseCode.ERROR)
                .entity(null)
                .message(message)
                .count(0)
                .build();
    }

    public static <T> ApiResponse<T> exception(Exception e) {
        return ApiResponse.<T>builder()
                .apiResponseCode(ApiResponseCode.ERROR)
                .entity(null)
                .message(e.getLocalizedMessage())
                .exception(ExceptionUtils.getStackTrace(e))
                .count(0)
                .build();
    }

    public static <T> ApiResponse<T> generate(DBResult<T> result) {
        switch (result.getCode()) {
            case OK:
                return ok(result);
            case WARNING:
                return databaseWarning(result.getMessage());
            case ERROR:
                return databaseError(result.getMessage());
        }
        return databaseError(result.getMessage());
    }
}
