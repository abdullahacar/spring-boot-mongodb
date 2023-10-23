package com.abdullahacar.springBootMongo.repository.auth;

import com.abdullahacar.springBootMongo.entity.Token;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TokenRepository extends MongoRepository<Token, String>, TokenCustomRepository {

  Optional<Token> findByTokenKey(String tokenKey);

}
