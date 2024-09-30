package com.nkm.mypracticespring.repositories;

import com.nkm.mypracticespring.models.ProductModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IProductRepository extends MongoRepository<ProductModel, String> {
}
