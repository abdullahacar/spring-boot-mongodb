package com.abdullahacar.springBootMongo.repository;

import com.abdullahacar.springBootMongo.dto.querymodel.*;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MongoQueryOrganizer {

    public static void handleQuery(QueryModel queryModel, Query query) {

        Optional.ofNullable(queryModel.get_ids()).ifPresent((t) -> {

            List<ObjectId> idList = t.stream().map(ObjectId::new).collect(Collectors.toList());

            query.addCriteria(Criteria.where("_id").in(idList));

        });

        Optional.ofNullable(queryModel.getNinModel()).ifPresent((t) -> query.addCriteria(Criteria.where(t.getFieldName()).nin(t.getValues())));

        Optional.ofNullable(queryModel.getNinModels()).ifPresent((t) -> {


            for (NinModel nm : t) {

                query.addCriteria(Criteria.where(nm.getFieldName()).nin(nm.getValues()));

            }


        });

        Optional.ofNullable(queryModel.getInModel()).ifPresent((t) -> {

            if (queryModel.getInModel().isId()) {

                ArrayList<ObjectId> ids = new ArrayList<>();

                t.getValues().forEach(v -> ids.add(new ObjectId(v.toString())));

                query.addCriteria(Criteria.where(t.getFieldName()).in(ids));

                return;

            }

            query.addCriteria(Criteria.where(t.getFieldName()).in(t.getValues()));


        });

        Optional.ofNullable(queryModel.getInModels()).ifPresent((t) -> {

            for (InModel im : t) {

                if (im.isId()) {

                    ArrayList<ObjectId> ids = new ArrayList<>();

                    im.getValues().forEach(v -> ids.add(new ObjectId(v.toString())));

                    query.addCriteria(Criteria.where(im.getFieldName()).in(ids));

                    continue;

                }

                if (im.isBool()) {

                    ArrayList<Boolean> values = new ArrayList<>();

                    im.getValues().forEach(v -> values.add(Boolean.valueOf(v.toString())));

                    query.addCriteria(Criteria.where(im.getFieldName()).in(values));

                    continue;

                }

                List valuesWithPattern = new ArrayList();

                for (Object v : im.getValues()) {

                    valuesWithPattern.add(Pattern.compile(v.toString(), Pattern.CASE_INSENSITIVE));

                }

                query.addCriteria(Criteria.where(im.getFieldName()).in(valuesWithPattern));

            }

        });

        Optional.ofNullable(queryModel.getExistsModels()).ifPresent((t) -> {

            for (ExistsModel em : queryModel.getExistsModels()) {

                query.addCriteria(Criteria.where(em.getFieldName()).exists(em.isExists()));

            }

        });

        Optional.ofNullable(queryModel.getBetweenDatesModel()).ifPresent((t) -> query.addCriteria(Criteria.where(t.getFieldName()).gte(t.getStartDate()).and(t.getFieldName()).lt(t.getEndDate())));

        Optional.ofNullable(queryModel.getOrModels()).ifPresent((t) -> {

            if (t.isEmpty()) {

                return;

            }

            List<Criteria> finalList = new ArrayList<>();

            for (OrModel orModel : t) {

                List<String> values = orModel.getValues();

                List<Criteria> collect = orModel.getFieldNames().stream().map(s -> Criteria.where(s).in(values)).toList();

                finalList.addAll(collect);

            }

            query.addCriteria(new Criteria().orOperator(finalList));

        });


    }

    public static List<Sort.Order> getSort(QueryModel queryModel) {

        if (queryModel.getSort() == null) {
            return List.of();
        }

        List<Sort.Order> orders = new ArrayList<>();

        queryModel.getSort().forEach(s -> {

            Sort.Order order = new Sort.Order(s.getOrder() == -1 ? Sort.Direction.DESC : Sort.Direction.ASC, s.getFieldName());
            orders.add(order);
        });

        return orders;
    }


}
