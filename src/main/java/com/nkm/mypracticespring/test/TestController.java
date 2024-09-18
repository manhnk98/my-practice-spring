package com.nkm.mypracticespring.test;

import com.nkm.mypracticespring.dto.ResponseDto;
import com.nkm.mypracticespring.test.mysql.MysqlUserRepository;
import com.nkm.mypracticespring.test.postgres.PostgresUserEntity;
import com.nkm.mypracticespring.test.postgres.PostgresUserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private PostgresUserRepository postgresUserRepository;

    @Autowired
    private MysqlUserRepository mysqlUserRepository;

//    @Autowired
//    private HandlerUserMysql handlerUserMysql;
//
//    @Autowired
//    private HandlerUserPostgres handlerUserPostgres;

    @SneakyThrows
    @PostMapping("/init-data")
    public ResponseDto<?> initData() {
        BlockingQueue<Integer> queueIndex = new LinkedBlockingQueue<>();
        for (int i = 0; i < 1_000_000; i++) {
            queueIndex.add(i);
        }

        System.out.println("Init done queueIndex");

        int sizeOfProducer = 6;
        int sizeOfConsumer = 6;

        ExecutorService executorProducer = Executors.newFixedThreadPool(sizeOfProducer);
        ExecutorService executorConsumer = Executors.newFixedThreadPool(sizeOfConsumer);
        BlockingQueue<PostgresUserEntity> queueDataPostgres = new LinkedBlockingQueue<>();
//        BlockingQueue<MysqlUserEntity> queueDataMysql = new LinkedBlockingQueue<>();

        long timeStart = System.currentTimeMillis();

        String password = this.generateString(500);
        String text = this.generateString(10000);

        for (int i = 0; i < sizeOfProducer; i++) {
            executorProducer.submit(() -> {
                while (!queueIndex.isEmpty()) {
                    Integer index;
                    try {
                        index = queueIndex.poll(1, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    if (index != null) {
                        try {
                            PostgresUserEntity postgresUserEntity = new PostgresUserEntity();
                            postgresUserEntity.setFullName("Nguyen Van Manh Postgresql" + index);
                            postgresUserEntity.setUsername("ManhNV_" + index);
                            postgresUserEntity.setPassword(password);
                            postgresUserEntity.setEmail("manhnv_" + index + "@gmail.com");
                            postgresUserEntity.setTestText(text);
                            queueDataPostgres.add(postgresUserEntity);

//                        MysqlUserEntity mysqlUserEntity = new MysqlUserEntity();
//                        mysqlUserEntity.setFullName("Nguyen Van Manh MYSQL" + index);
//                        mysqlUserEntity.setUsername("ManhNV_" + index);
//                        mysqlUserEntity.setPassword(password);
//                        mysqlUserEntity.setEmail("manhnv_" + index + "@gmail.com");
//                        mysqlUserEntity.setTestText(text);
//                        queueDataMysql.add(mysqlUserEntity);
                        } catch (Exception e) {
                            System.out.println("ERROR index: " + index);
                        }
                    }
                }

            });

        }

//        while (!queueIndex.isEmpty()) {
//            executorProducer.submit(() -> {
//                Integer index;
//                try {
//                    index = queueIndex.poll(1, TimeUnit.SECONDS);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//
//                if (index != null) {
//                    try {
//                        PostgresUserEntity postgresUserEntity = new PostgresUserEntity();
//                        postgresUserEntity.setFullName("Nguyen Van Manh Postgresql" + index);
//                        postgresUserEntity.setUsername("ManhNV_" + index);
//                        postgresUserEntity.setPassword(password);
//                        postgresUserEntity.setEmail("manhnv_" + index + "@gmail.com");
//                        postgresUserEntity.setTestText(text);
//                        queueDataPostgres.add(postgresUserEntity);
//
////                        MysqlUserEntity mysqlUserEntity = new MysqlUserEntity();
////                        mysqlUserEntity.setFullName("Nguyen Van Manh MYSQL" + index);
////                        mysqlUserEntity.setUsername("ManhNV_" + index);
////                        mysqlUserEntity.setPassword(password);
////                        mysqlUserEntity.setEmail("manhnv_" + index + "@gmail.com");
////                        mysqlUserEntity.setTestText(text);
////                        queueDataMysql.add(mysqlUserEntity);
//                    } catch (Exception e) {
//                        System.out.println("ERROR index: " + index);
//                    }
//                }
//            });
//        }
        executorProducer.shutdown();
        boolean terminateProducer = executorProducer.awaitTermination(1, TimeUnit.HOURS);
        if (terminateProducer) {
            System.out.println("terminateProducer");
        }

        for (int i = 0; i < sizeOfConsumer; i++) {
//            executorConsumer.submit(new HandlerUserMysql(mysqlUserRepository, queueDataMysql));
            executorConsumer.submit(new HandlerUserPostgres(postgresUserRepository, queueDataPostgres));
        }

        executorConsumer.shutdown();
        boolean terminateConsumer = executorConsumer.awaitTermination(1, TimeUnit.HOURS);
        if (terminateConsumer) {
            System.out.println("terminateConsumer");
        }

        long timeEnd = System.currentTimeMillis();

        System.out.println("Time handle : " + TimeUnit.MILLISECONDS.toSeconds(timeEnd - timeStart) + " seconds");

        return new ResponseDto<>("ok");
    }


    public String generateString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }


}
