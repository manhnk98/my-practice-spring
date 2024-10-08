package com.nkm.mypracticespring.repositories.custom;

import com.nkm.mypracticespring.models.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositoryCustom {

    Page<ProductModel> getAllDraftsForShop(String shopId, Pageable pageable);

    Page<ProductModel> getAllPublishedForShop(String shopId, Pageable pageable);

    Page<ProductModel> searchProduct(String keySearch, Pageable pageable);

    Page<ProductModel> findAllProducts(String shopId, List<String> fields, Pageable pageable);

    ProductModel findOneProduct(String shopId, String productId, List<String> fieldsToExclude);
}
