package com.nkm.mypracticespring.test.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MysqlUserRepository extends JpaRepository<MysqlUserEntity, String> {
}
