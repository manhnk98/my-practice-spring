package com.nkm.mypracticespring.repositories;

import com.nkm.mypracticespring.models.ClothesModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IClothesRepository extends MongoRepository<ClothesModel, String> {
}
