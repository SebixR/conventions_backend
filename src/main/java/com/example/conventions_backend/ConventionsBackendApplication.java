package com.example.conventions_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class ConventionsBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConventionsBackendApplication.class, args);
    }

//    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
//    public void updateStatuses() {
//
//    }
}
