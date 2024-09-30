package com.nkm.mypracticespring.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "electronics")
public class ElectronicModel extends BaseModel {

    @Field("manufacturer")
    private String manufacturer;

    @Field("model")
    private String model;

    @Field("color")
    private String color;

    @DBRef
    @Field("product_shop")
    private ShopModel productShop;

}
