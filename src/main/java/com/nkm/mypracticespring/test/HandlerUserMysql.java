package com.nkm.mypracticespring.test;

import com.nkm.mypracticespring.test.mysql.MysqlUserEntity;
import com.nkm.mypracticespring.test.mysql.MysqlUserRepository;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class HandlerUserMysql implements Runnable {

    private MysqlUserRepository mysqlUserRepository;

    private BlockingQueue<MysqlUserEntity> queueUserMysql;

    public HandlerUserMysql(MysqlUserRepository mysqlUserRepository, BlockingQueue<MysqlUserEntity> queueUserMysql) {
        this.mysqlUserRepository = mysqlUserRepository;
        this.queueUserMysql = queueUserMysql;
    }

    @SneakyThrows
    @Override
    public void run() {
        List<MysqlUserEntity> listUser = new ArrayList<>();
        while (!queueUserMysql.isEmpty()) {
            if (listUser.size() == 1000) {
                mysqlUserRepository.saveAll(listUser);
                System.out.println("Insert Mysql " + listUser.getLast().getUsername());
                listUser = new ArrayList<>();
            } else {
                MysqlUserEntity user = queueUserMysql.poll(1, TimeUnit.SECONDS);
                if (user != null) {
                    listUser.add(user);
                } else {
                    break;
                }
            }
        }

        if (!listUser.isEmpty()) {
            mysqlUserRepository.saveAll(listUser);
        }

    }
}
