package com.example.db_key_value;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DbKeyValueApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbKeyValueApplication.class, args);
    }

}
