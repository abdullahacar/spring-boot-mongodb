package com.abdullahacar.springBootMongo.repository.delivery;

import com.abdullahacar.springBootMongo.entity.Delivery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  DeliveryRepository extends MongoRepository<Delivery, String>, DeliveryRepositoryCustom {


}
