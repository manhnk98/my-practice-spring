package com.nkm.mypracticespring.repositories;

import com.nkm.mypracticespring.models.ShopModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShopRepository extends MongoRepository<ShopModel, String> {
}
