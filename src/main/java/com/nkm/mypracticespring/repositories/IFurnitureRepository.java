package com.nkm.mypracticespring.repositories;

import com.nkm.mypracticespring.models.FurnitureModel;
import com.nkm.mypracticespring.models.ShopModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IFurnitureRepository extends MongoRepository<FurnitureModel, String> {
    FurnitureModel findFirstByIdAndProductShop(String id, ShopModel shop);
}
