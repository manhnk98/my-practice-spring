package com.nkm.mypracticespring.repositories;

import com.nkm.mypracticespring.models.SessionModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ISessionRepository extends MongoRepository<SessionModel, String> {

}
