package com.abdullahacar.springBootMongo.service;

import com.abdullahacar.springBootMongo.dto.ApiResponse;
import com.abdullahacar.springBootMongo.dto.DBResult;
import com.abdullahacar.springBootMongo.dto.delivery.DeliveryDTO;
import com.abdullahacar.springBootMongo.dto.querymodel.Delivery.DeliveryQueryModel;
import com.abdullahacar.springBootMongo.repository.delivery.    DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    public ApiResponse<List<DeliveryDTO>> getDeliveryByParameters(DeliveryQueryModel queryModel) {

        DBResult<List<DeliveryDTO>> result = deliveryRepository.query(queryModel);

        return ApiResponseGenerator.generate(result);

    }


}
