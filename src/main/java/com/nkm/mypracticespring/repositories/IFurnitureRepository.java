package com.nkm.mypracticespring.repositories;

import com.nkm.mypracticespring.models.FurnitureModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IFurnitureRepository extends MongoRepository<FurnitureModel, String> {
}
