package com.nkm.mypracticespring.services;

import com.nkm.mypracticespring.dto.product.ProductCreateRequest;
import com.nkm.mypracticespring.enums.ProductTypeEnum;
import com.nkm.mypracticespring.models.ShopModel;

public interface IProductService {

    String createProduct(ProductTypeEnum productType, ProductCreateRequest request, String shopId);

    void updateProduct(ProductTypeEnum productType, ProductCreateRequest request);

    Object findAllDraftsForShop(String shopId, Integer page, Integer size);

    Object publishProduct(String productId, String shopId);

    Object getAllPublishedForShop(String shopId, Integer page, Integer size);

}
