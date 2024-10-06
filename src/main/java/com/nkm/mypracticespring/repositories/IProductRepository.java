package com.nkm.mypracticespring.repositories;

import com.nkm.mypracticespring.models.ProductModel;
import com.nkm.mypracticespring.models.ShopModel;
import com.nkm.mypracticespring.repositories.custom.IProductRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IProductRepository extends MongoRepository<ProductModel, String>, IProductRepositoryCustom {

    Optional<ProductModel> findByIdAndProductShop(String id, ShopModel shop);

}
