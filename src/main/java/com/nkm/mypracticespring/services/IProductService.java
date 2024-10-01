package com.nkm.mypracticespring.services;

import com.nkm.mypracticespring.enums.ProductTypeEnum;

public interface IProductService {

    String createProduct(ProductTypeEnum productType, Object payload);

    void updateProduct(ProductTypeEnum productType, Object payload);

}