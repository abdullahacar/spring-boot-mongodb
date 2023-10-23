package com.abdullahacar.springBootMongo.config;

import com.abdullahacar.springBootMongo.dto.querymodel.Login.LoginQueryModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class LoginQueryModelDeserializer implements Converter<String, LoginQueryModel> {

    private final ObjectMapper mapper;

    @Autowired
    public LoginQueryModelDeserializer() {
        this.mapper = new ObjectMapper();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @SneakyThrows
    @Override
    public  LoginQueryModel convert(String json) {

        return this.mapper.readValue(json, LoginQueryModel.class);

    }

}
