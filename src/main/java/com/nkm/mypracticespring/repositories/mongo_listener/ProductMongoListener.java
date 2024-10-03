package com.nkm.mypracticespring.repositories.mongo_listener;

import com.nkm.mypracticespring.models.ProductModel;
import com.nkm.mypracticespring.utils.CommonUtils;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class ProductMongoListener extends AbstractMongoEventListener<ProductModel> {

    @Override
    public void onBeforeConvert(BeforeConvertEvent<ProductModel> event) {
        ProductModel productModel = event.getSource();
        productModel.setProductSlug(CommonUtils.toSlug(productModel.getProductName()));
        super.onBeforeConvert(event);
    }
}
