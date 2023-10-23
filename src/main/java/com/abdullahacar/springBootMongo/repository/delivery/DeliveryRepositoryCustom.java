package com.abdullahacar.springBootMongo.repository.delivery;

import com.abdullahacar.springBootMongo.dto.DBResult;
import com.abdullahacar.springBootMongo.dto.delivery.DeliveryDTO;
import com.abdullahacar.springBootMongo.dto.querymodel.Delivery.DeliveryQueryModel;

import java.util.List;

public interface DeliveryRepositoryCustom {

    DBResult<List<DeliveryDTO>> query(DeliveryQueryModel queryModel);

}
