package com.nkm.mypracticespring.repositories;

import com.nkm.mypracticespring.models.KeyTokenModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface KeyTokenRepository extends MongoRepository<KeyTokenModel, String> {

    KeyTokenModel findFirstByUserId(String userId);

}
