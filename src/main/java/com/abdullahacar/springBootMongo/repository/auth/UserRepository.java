package com.abdullahacar.springBootMongo.repository.auth;

import com.abdullahacar.springBootMongo.entity.Login;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<Login, String> {

    Optional<Login> findByUsername(String username);

}
