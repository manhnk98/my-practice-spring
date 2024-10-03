package com.nkm.mypracticespring.services;

import com.nkm.mypracticespring.dto.product.ProductCreateRequest;
import com.nkm.mypracticespring.enums.ProductTypeEnum;

public interface IProductService {

    String createProduct(ProductTypeEnum productType, ProductCreateRequest request, String shopId);

    void updateProduct(ProductTypeEnum productType, ProductCreateRequest request);

    Object findAllDraftsForShop(String shopId, Integer limit, Integer skip);

}
