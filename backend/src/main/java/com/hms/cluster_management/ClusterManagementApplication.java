package com.hms.cluster_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class ClusterManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(
            ClusterManagementApplication.class, args
        );
    }

    @Bean
    CommandLineRunner checkMongoDatabase(MongoTemplate mongoTemplate) {
        return args -> {
            System.out.println(
                "Connected MongoDB: " +
                mongoTemplate.getDb().getName()
            );
        };
    }
}