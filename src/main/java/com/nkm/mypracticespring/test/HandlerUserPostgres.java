//package com.nkm.mypracticespring.test;
//
//import com.nkm.mypracticespring.test.postgres.PostgresUserEntity;
//import com.nkm.mypracticespring.test.postgres.PostgresUserRepository;
//import lombok.SneakyThrows;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.TimeUnit;
//
//
//public class HandlerUserPostgres implements Runnable {
//
//    private PostgresUserRepository postgresUserRepository;
//
//    private BlockingQueue<PostgresUserEntity> queueUser;
//
//    public HandlerUserPostgres(PostgresUserRepository postgresUserRepository, BlockingQueue<PostgresUserEntity> queueUser) {
//        this.postgresUserRepository = postgresUserRepository;
//        this.queueUser = queueUser;
//    }
//
//    @SneakyThrows
//    @Override
//    public void run() {
//        List<PostgresUserEntity> listUser = new ArrayList<>();
//        while (!queueUser.isEmpty()) {
//            if (listUser.size() == 1000) {
//                postgresUserRepository.saveAll(listUser);
//                System.out.println("Insert Postgresql " + listUser.getLast().getUsername());
//                listUser = new ArrayList<>();
//            } else {
//                PostgresUserEntity user = queueUser.poll(1, TimeUnit.SECONDS);
//                if (user != null) {
//                    listUser.add(user);
//                } else {
//                    break;
//                }
//            }
//        }
//        if (!listUser.isEmpty()) {
//            postgresUserRepository.saveAll(listUser);
//        }
//    }
//}
