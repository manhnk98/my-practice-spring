package com.nkm.mypracticespring.test.postgres;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostgresUserRepository extends JpaRepository<PostgresUserEntity, String> {
}
