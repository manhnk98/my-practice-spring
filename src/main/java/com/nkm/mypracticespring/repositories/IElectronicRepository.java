package com.nkm.mypracticespring.repositories;

import com.nkm.mypracticespring.models.ElectronicModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IElectronicRepository extends MongoRepository<ElectronicModel, String> {
}
