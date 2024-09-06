package com.nkm.mypracticespring.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "key_tokens")
public class KeyTokenModel extends BaseModel {

    @DBRef
    private ShopModel user;

    private String privateKey;

    private String publicKey;

    private List<String> refreshTokensUsed;

    private String refreshToken;

}
