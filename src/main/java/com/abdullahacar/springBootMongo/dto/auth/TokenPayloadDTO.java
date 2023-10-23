package com.abdullahacar.springBootMongo.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenPayloadDTO {

    String loginId;
    String imsId;
    String username;

}
