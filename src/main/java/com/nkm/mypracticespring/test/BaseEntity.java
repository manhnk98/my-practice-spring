//package com.nkm.mypracticespring.test;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import java.time.LocalDateTime;
//
//@MappedSuperclass
//@EntityListeners(AuditingEntityListener.class)
//@Data
//public class BaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    @Column(name = "id", length = 36)
//    private String id;
//
//    @CreatedDate
//    @Column(name = "created_at", nullable = false)
//    private LocalDateTime createdAt;
//
//    @LastModifiedDate
//    @Column(name = "updated_at", nullable = false)
//    private LocalDateTime updatedAt;
//
//}
