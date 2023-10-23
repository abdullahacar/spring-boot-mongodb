package com.abdullahacar.springBootMongo.mappers;

import com.abdullahacar.springBootMongo.dto.delivery.DeliveryDTO;
import com.abdullahacar.springBootMongo.dto.delivery.DeliveryStatusDTO;
import com.abdullahacar.springBootMongo.dto.delivery.DeliveryTypeDTO;
import com.abdullahacar.springBootMongo.dto.delivery.ImsDTO;
import com.abdullahacar.springBootMongo.entity.Delivery;

public class Mapper {

    public static DeliveryDTO map(Delivery delivery) {
        return DeliveryDTO.builder()
                .deliveryNo(delivery.getDeliveryNo())
                .id(delivery.getId())
                .status(DeliveryStatusDTO.valueOf(delivery.getStatus().name()))
                .type(DeliveryTypeDTO.valueOf(delivery.getType().name()))
                .consignor(ImsDTO.builder()
                        .firstName(delivery.getConsignor().getFirstName())
                        .lastName(delivery.getConsignor().getLastName())
                        .address(delivery.getConsignor().getAddress())
                        .build())
                .consignee(ImsDTO.builder()
                        .firstName(delivery.getConsignee().getFirstName())
                        .lastName(delivery.getConsignee().getLastName())
                        .address(delivery.getConsignee().getAddress())
                        .build())
                .createdDate(delivery.getCreatedDate())
                .integrated(delivery.isIntegrated())
                .build();
    }

}
