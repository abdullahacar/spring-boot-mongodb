package com.abdullahacar.springBootMongo.config;

import com.abdullahacar.springBootMongo.dto.querymodel.Delivery.DeliveryQueryModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class DeliveryQueryModelDeserializer implements Converter<String, DeliveryQueryModel> {

    private final ObjectMapper mapper;

    @Autowired
    public DeliveryQueryModelDeserializer() {
        this.mapper = new ObjectMapper();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @SneakyThrows
    @Override
    public DeliveryQueryModel convert(String json) {

        return mapper.readValue(json, DeliveryQueryModel.class);

    }

}
