package com.nkm.mypracticespring.models;

import com.nkm.mypracticespring.enums.StatusShopEnum;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "shops")
public class ShopModel extends BaseModel {

    private String name;

    @Indexed(unique = true)
    private String email;

    private String password;

    private StatusShopEnum status = StatusShopEnum.INACTIVE;

    private Boolean verify = false;

    private List<String> roles = new ArrayList<>();

}
