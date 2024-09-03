package com.nkm.mypracticespring.config;

import com.mongodb.ConnectionString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoConfig {

    @Bean
    public MongoTemplate mongoTemplate() {
        String username = "root";
        String password = "admin";
        int port = 27017;
        String host = "localhost";
        String database = "e-commerce";
        String conString = "mongodb://" + username + ":" + password + "@" + host + ":" + port + "/" + database + "?authSource=admin"; // + "/?authSource=admin"

        ConnectionString connectionString = new ConnectionString(conString);


        MongoDatabaseFactory mongoDatabaseFactory = new SimpleMongoClientDatabaseFactory(connectionString);
        return new MongoTemplate(mongoDatabaseFactory);

//        MongoCredential credential = MongoCredential.createCredential(username, database, password.toCharArray());
//        MongoClient mongoClient = MongoClients.create(
//                MongoClientSettings.builder()
//                        .applyToClusterSettings(builder -> builder.hosts(Collections.singletonList(new ServerAddress(host, port))))
//                        .credential(credential)
//                        .build());

//        return new MongoTemplate(new SimpleMongoClientDatabaseFactory(mongoClient, database));
    }
}
