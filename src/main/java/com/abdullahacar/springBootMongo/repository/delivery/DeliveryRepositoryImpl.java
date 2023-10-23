package com.abdullahacar.springBootMongo.repository.delivery;

import com.abdullahacar.springBootMongo.dto.DBResult;
import com.abdullahacar.springBootMongo.dto.DBResultCode;
import com.abdullahacar.springBootMongo.dto.delivery.DeliveryDTO;
import com.abdullahacar.springBootMongo.dto.querymodel.Delivery.DeliveryQueryModel;
import com.abdullahacar.springBootMongo.entity.Delivery;
import com.abdullahacar.springBootMongo.mappers.Mapper;
import com.abdullahacar.springBootMongo.repository.MongoQueryOrganizer;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DeliveryRepositoryImpl implements DeliveryRepositoryCustom {

    @Autowired
    private final MongoTemplate mongoTemplate;

    @Autowired
    private final ModelMapper mapper;

    @Override
    public DBResult<List<DeliveryDTO>> query(DeliveryQueryModel queryModel) {

        Query query = new Query();

        Optional.ofNullable(queryModel.getId()).ifPresent(id -> query.addCriteria(Criteria.where("_id").is(new ObjectId(id))));
        Optional.ofNullable(queryModel.getStatus()).ifPresent(status -> query.addCriteria(Criteria.where("status").is(status)));
        Optional.ofNullable(queryModel.getDeliveryNo()).ifPresent(deliveryNo -> query.addCriteria(Criteria.where("deliveryNo").regex(deliveryNo)));
        Optional.ofNullable(queryModel.getConsigneeFirstName()).ifPresent(fn -> query.addCriteria(Criteria.where("consignee.firstName").regex(fn)));
        Optional.ofNullable(queryModel.getConsigneeLastName()).ifPresent(ln -> query.addCriteria(Criteria.where("consignee.lastName").regex(ln)));
        Optional.ofNullable(queryModel.getConsigneeAddress()).ifPresent(address -> query.addCriteria(Criteria.where("consignee.address").regex(address)));
        Optional.ofNullable(queryModel.getConsignorFirstName()).ifPresent(fn -> query.addCriteria(Criteria.where("consignor.firstName").regex(fn)));
        Optional.ofNullable(queryModel.getConsignorLastName()).ifPresent(ln -> query.addCriteria(Criteria.where("consignor.lastName").regex(ln)));
        Optional.ofNullable(queryModel.getConsignorAddress()).ifPresent(address -> query.addCriteria(Criteria.where("consignor.address").regex(address)));
        Optional.ofNullable(queryModel.getType()).ifPresent(type -> query.addCriteria(Criteria.where("type").is(type)));
        Optional.ofNullable(queryModel.getIntegrated()).ifPresent(integrated -> query.addCriteria(Criteria.where("integrated").is(integrated)));


        if (queryModel.getCreatedDateStart() != null && queryModel.getCreatedDateEnd() != null) {

            query.addCriteria(new Criteria()
                    .andOperator(
                            Criteria.where("createdDate").gte(queryModel.getCreatedDateStart()),
                            Criteria.where("createdDate").lte(queryModel.getCreatedDateEnd())
                    )
            );

        } else {
            Optional.ofNullable(
                    queryModel.getCreatedDateStart()).ifPresent(createdDate -> query.addCriteria(Criteria.where("createdDate")
                    .gte(queryModel.getCreatedDateStart())));

            Optional.ofNullable(
                    queryModel.getCreatedDateEnd()).ifPresent(endDate -> query.addCriteria(Criteria.where("createdDate").lte(queryModel.getCreatedDateEnd())));
        }


        MongoQueryOrganizer.handleQuery(queryModel, query);

        long count = 0;
        if (queryModel.isWithCount()) {
            count = mongoTemplate.count(query, Delivery.class);
        }

        query.with(Sort.by(MongoQueryOrganizer.getSort(queryModel)))
                .limit(queryModel.getLimit())
                .skip(queryModel.getSkip());

        List<Delivery> deliveries = mongoTemplate.find(query, Delivery.class);

        return DBResult.<List<DeliveryDTO>>builder()
                .code(DBResultCode.OK)
                .entity(deliveries.stream().map(delivery -> mapper.map(delivery, DeliveryDTO.class)).collect(Collectors.toList()))
                .count(count)
                .build();

    }
}
