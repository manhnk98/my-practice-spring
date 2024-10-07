package com.nkm.mypracticespring.models;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;

@Data
@Document(collection = "products")
public class ProductModel extends BaseModel {

    @Indexed
    @Field("product_name")
    private String productName;

    @Field("product_thumb")
    private String productThumb;

    @Indexed
    @Field("product_description")
    private String productDescription;

    @Field("product_slug")
    private String productSlug;

    @Field("product_price")
    private Double productPrice;

    @Field("product_quality")
    private Integer productQuality;

    @Field("product_type")
    private String productType;

    @DBRef
    @Field("product_shop")
    private ShopModel productShop;

    @Field("product_attributes")
    private Map<String, Object> productAttributes;

    @Field("product_ratingsAverage")
    private Double productRatingsAverage = 4.5;

    @Field("product_variations")
    private List<String> productVariations;

    @Indexed
    @Field("is_draft")
    private Boolean isDraft = true;

    @Indexed
    @Field("is_published")
    private Boolean isPublished = false;

    public void setProductRatingsAverage(Double rating) {
        if (rating != null) {
            this.productRatingsAverage = Math.round(rating * 10) / 10.0;
        }
    }

}
