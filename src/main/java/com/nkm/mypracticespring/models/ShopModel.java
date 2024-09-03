package com.nkm.mypracticespring.models;

import com.nkm.mypracticespring.enums.StatusShopEnum;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "shops")
@Data
public class ShopModel {

    @Id
    private String id;

    private String name;

    @Indexed(unique = true)
    private String email;

    private String password;

    private StatusShopEnum status = StatusShopEnum.INACTIVE;

    private Boolean verify = false;

    private List<String> roles = new ArrayList<>();

}
