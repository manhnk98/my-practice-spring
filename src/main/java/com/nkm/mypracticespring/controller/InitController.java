package com.nkm.mypracticespring.controller;

import com.nkm.mypracticespring.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health-check")
public class InitController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping
    public Object healthCheck() {
        UserModel user = new UserModel();
        user.setEmail("nkm081198");
        user.setName("manhnk");
        return mongoTemplate.save(user);
    }

}
