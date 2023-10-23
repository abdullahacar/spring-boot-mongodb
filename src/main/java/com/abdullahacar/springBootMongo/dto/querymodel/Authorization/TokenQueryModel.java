package com.abdullahacar.springBootMongo.dto.querymodel.Authorization;

import com.abdullahacar.springBootMongo.dto.querymodel.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Super;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
public class TokenQueryModel extends QueryModel {

    String loginId;

}
