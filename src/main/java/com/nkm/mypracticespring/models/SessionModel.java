package com.nkm.mypracticespring.models;

import com.nkm.mypracticespring.enums.SessionStatus;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "key_tokens")
public class SessionModel extends BaseModel {

    private String userId;

    private SessionStatus status;

}
