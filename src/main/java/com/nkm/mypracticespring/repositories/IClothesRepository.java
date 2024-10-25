package com.nkm.mypracticespring.repositories;

import com.nkm.mypracticespring.models.ClothesModel;
import com.nkm.mypracticespring.models.ShopModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IClothesRepository extends MongoRepository<ClothesModel, String> {
    ClothesModel findFirstByIdAndProductShop(String id, ShopModel shop);
}
