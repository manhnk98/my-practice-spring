package com.nkm.mypracticespring.repositories;

import com.nkm.mypracticespring.models.ElectronicModel;
import com.nkm.mypracticespring.models.ShopModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IElectronicRepository extends MongoRepository<ElectronicModel, String> {
    ElectronicModel findFirstByIdAndProductShop(String id, ShopModel shop);
}
