package com.nkm.mypracticespring.repositories;

import com.nkm.mypracticespring.models.ProductModel;
import com.nkm.mypracticespring.models.ShopModel;
import com.nkm.mypracticespring.repositories.custom.ProductRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<ProductModel, String>, ProductRepositoryCustom {

    Optional<ProductModel> findByIdAndProductShop(String id, ShopModel shop);

    Page<ProductModel> findAllByProductShopAndIsDraftIsTrue(ShopModel shop, Pageable pageable);

    Page<ProductModel> findAllByProductShopAndIsPublishedIsTrue(ShopModel shop, Pageable pageable);

    @Query(value = "{ 'isPublished': true, $text: { $search: ?0 } }", sort = "{ score: { $meta: 'textScore' } }")
    List<ProductModel> searchByText(String keySearch);

}
