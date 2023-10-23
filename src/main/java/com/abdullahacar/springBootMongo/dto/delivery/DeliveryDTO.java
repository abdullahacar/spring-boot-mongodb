package com.abdullahacar.springBootMongo.dto.delivery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryDTO {

    private String id;
    private Date createdDate;
    private String deliveryNo;
    private DeliveryStatusDTO status;
    private DeliveryTypeDTO type;
    private ImsDTO consignor;
    private ImsDTO consignee;
    private boolean integrated;

}
