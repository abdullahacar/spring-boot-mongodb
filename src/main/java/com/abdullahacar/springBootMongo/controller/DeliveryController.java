package com.abdullahacar.springBootMongo.controller;


import com.abdullahacar.springBootMongo.dto.ApiResponse;
import com.abdullahacar.springBootMongo.dto.delivery.DeliveryDTO;
import com.abdullahacar.springBootMongo.dto.querymodel.Delivery.DeliveryQueryModel;
import com.abdullahacar.springBootMongo.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @PostMapping("/getDeliveryByParameters")
    public ResponseEntity<ApiResponse<List<DeliveryDTO>>> getDeliveryByParameters(@RequestBody DeliveryQueryModel queryModel) {

        return ResponseEntity.ok(deliveryService.getDeliveryByParameters(queryModel));

    }


}
