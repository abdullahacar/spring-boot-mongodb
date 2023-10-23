package com.abdullahacar.springBootMongo.entity;

import com.abdullahacar.springBootMongo.dto.delivery.DeliveryStatusDTO;
import com.abdullahacar.springBootMongo.dto.delivery.DeliveryTypeDTO;
import com.abdullahacar.springBootMongo.dto.delivery.ImsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "Delivery_doc")
public class Delivery {

    @Id
    private String id;
    private Date createdDate;
    private String deliveryNo;
    private DeliveryStatus status;
    private DeliveryType type;
    private Ims consignor;
    private Ims consignee;
    private boolean integrated;

}
