package com.nkm.mypracticespring.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
public class UserModel {

    @Id
    private String id;
    private String name;
    private String email;

}
