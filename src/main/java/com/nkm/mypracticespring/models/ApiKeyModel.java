package com.nkm.mypracticespring.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "api_keys")
public class ApiKeyModel extends BaseModel {

    private Boolean status;

    private List<String> permissions;

}
