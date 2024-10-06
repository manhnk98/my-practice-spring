package com.nkm.mypracticespring.repositories.custom;

import com.nkm.mypracticespring.models.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductRepositoryCustom {

    Page<ProductModel> getAllDraftsForShop(String shopId, Pageable pageable);

    Page<ProductModel> getAllPublishedForShop(String shopId, Pageable pageable);
}
