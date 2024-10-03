package com.nkm.mypracticespring.repositories.custom;

import com.nkm.mypracticespring.models.ProductModel;

import java.util.List;

public interface IProductRepositoryCustom {
    List<ProductModel> findAllDraftsForShop(String shopId, int limit, int skip);
}
