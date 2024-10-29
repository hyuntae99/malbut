package com.hyunn.malBut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MalButApplication {

    public static void main(String[] args) {
        SpringApplication.run(MalButApplication.class, args);
    }

}
