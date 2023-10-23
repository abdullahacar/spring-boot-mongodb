package com.abdullahacar.springBootMongo.repository.auth;

import com.abdullahacar.springBootMongo.dto.querymodel.Authorization.TokenQueryModel;
import com.abdullahacar.springBootMongo.entity.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TokenCustomRepositoryImpl implements TokenCustomRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public  List<Token> getAllActiveTokensByLoginId(TokenQueryModel queryModel) {

        Query query = new Query();

        Optional.ofNullable(queryModel.getLoginId()).ifPresent(id -> query.addCriteria(Criteria.where("loginId").is(id)));

        query.addCriteria(Criteria.where("active").is(true));

        return mongoTemplate.find(query, Token.class);
    }
}
