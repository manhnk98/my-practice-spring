package com.nkm.mypracticespring.models;

import com.nkm.mypracticespring.enums.SessionStatus;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "user_sessions")
public class SessionModel extends BaseModel {

    private String userId;

    private SessionStatus status;

    private LocalDateTime expireTime;

}
