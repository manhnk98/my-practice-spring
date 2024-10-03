package com.nkm.mypracticespring.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public abstract class BaseModel {

    @Id
    private String id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
