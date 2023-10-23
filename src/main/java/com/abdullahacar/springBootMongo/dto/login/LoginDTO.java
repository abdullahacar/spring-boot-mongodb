package com.abdullahacar.springBootMongo.dto.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDTO {

    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;

}
