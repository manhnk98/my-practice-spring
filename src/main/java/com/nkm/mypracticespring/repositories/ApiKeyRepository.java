package com.nkm.mypracticespring.repositories;

import com.nkm.mypracticespring.models.ApiKeyModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApiKeyRepository extends MongoRepository<ApiKeyModel, String> {
}
