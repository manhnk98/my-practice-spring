package com.nkm.mypracticespring.services;

import com.nkm.mypracticespring.dto.product.ProductCreateRequest;
import com.nkm.mypracticespring.enums.ProductTypeEnum;
import com.nkm.mypracticespring.models.ProductModel;
import org.springframework.data.domain.Page;

public interface IProductService {

    String createProduct(ProductTypeEnum productType, ProductCreateRequest request, String shopId);

    void updateProduct(ProductTypeEnum productType, ProductCreateRequest request);

    Page<ProductModel> searchProduct(String shopId, String keySearch, Integer page, Integer size);

    Page<ProductModel> findAllDraftsForShop(String shopId, Integer page, Integer size);

    Page<ProductModel> getAllPublishedForShop(String shopId, Integer page, Integer size);

    Object publishProduct(String productId, String shopId);

    Object unpublishProduct(String productId, String shopId);



}
