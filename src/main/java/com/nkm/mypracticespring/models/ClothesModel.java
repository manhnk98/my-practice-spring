package com.nkm.mypracticespring.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "clothes")
public class ClothesModel extends BaseModel {

    @Field("brand")
    private String brand;

    @Field("size")
    private String size;

    @Field("material")
    private String material;

    @DBRef
    @Field("product_shop")
    private ShopModel productShop;

}
