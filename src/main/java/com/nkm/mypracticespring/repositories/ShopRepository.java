package com.nkm.mypracticespring.repositories;

import com.nkm.mypracticespring.models.ShopModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ShopRepository extends MongoRepository<ShopModel, String> {

    Optional<ShopModel> findFirstByEmail(String email);

}
