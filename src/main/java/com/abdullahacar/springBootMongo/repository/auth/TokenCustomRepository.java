package com.abdullahacar.springBootMongo.repository.auth;

import com.abdullahacar.springBootMongo.dto.querymodel.Authorization.TokenQueryModel;
import com.abdullahacar.springBootMongo.entity.Token;

import java.util.List;

public interface TokenCustomRepository {

    List<Token> getAllActiveTokensByLoginId(TokenQueryModel queryModel);


}
