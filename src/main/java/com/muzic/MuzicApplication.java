package com.muzic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAsync
@EnableTransactionManagement(proxyTargetClass = true)
public class MuzicApplication {
    public static void main(String[] args) {
        SpringApplication.run(MuzicApplication.class, args);
    }
}
