package com.nkm.mypracticespring.test;

import com.nkm.mypracticespring.dto.ResponseDto;
import com.nkm.mypracticespring.test.mysql.MysqlUserEntity;
import com.nkm.mypracticespring.test.mysql.MysqlUserRepository;
import com.nkm.mypracticespring.test.postgres.PostgresUserEntity;
import com.nkm.mypracticespring.test.postgres.PostgresUserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private PostgresUserRepository postgresUserRepository;

    @Autowired
    private MysqlUserRepository mysqlUserRepository;

    @PostMapping("/init-data")
    public ResponseDto<?> initData() {
        long startTimeInit = System.currentTimeMillis();
        for (int i = 0; i < 100_000_000; i++) {
            System.out.println("Insert record index " + i);
            String password = this.generateString(500);
            String text = this.generateString(10000);

            PostgresUserEntity postgresUserEntity = new PostgresUserEntity();
            postgresUserEntity.setFullName("Nguyen Van Manh " + i);
            postgresUserEntity.setUsername("ManhNV_" + i);
            postgresUserEntity.setPassword(password);
            postgresUserEntity.setEmail("manhnv_" + i + "@gmail.com");
            postgresUserEntity.setTestText(text);
            postgresUserRepository.save(postgresUserEntity);

            MysqlUserEntity mysqlUserEntity = new MysqlUserEntity();
            mysqlUserEntity.setFullName("Nguyen Van Manh MYSQL" + i);
            mysqlUserEntity.setUsername("ManhNV_" + i);
            mysqlUserEntity.setPassword(password);
            mysqlUserEntity.setEmail("manhnv_" + i + "@gmail.com");
            mysqlUserEntity.setTestText(text);
            mysqlUserRepository.save(mysqlUserEntity);
        }
        long endTimeInit = System.currentTimeMillis();

        System.out.println("Time insert data : " + TimeUnit.MILLISECONDS.toSeconds(endTimeInit - startTimeInit));


        BlockingQueue<DataThread<MysqlUserEntity>> queueDataMysql = new LinkedBlockingQueue<>();
        BlockingQueue<DataThread<PostgresUserEntity>> queueDataPostgres = new LinkedBlockingQueue<>();

        int sizeOfProducer = 10;
        int sizeOfConsumer = 10;

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

    @Data
    private static class DataThread<T> {
        private List<T> dataMetric;

        public DataThread(List<T> dataMetric) {
            this.dataMetric = dataMetric;
        }
    }


}
