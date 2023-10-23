/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abdullahacar.springBootMongo.dto.querymodel.Login;

import com.abdullahacar.springBootMongo.dto.querymodel.QueryModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 *
 * @author ABDULLAH ACAR
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
public class LoginQueryModel extends QueryModel {

    String userName;
    String password;
    int version;
    boolean requireActive;
    String key;

}
