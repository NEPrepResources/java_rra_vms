package com.rra.gizzo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.rra")
public class SpringNeTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringNeTemplateApplication.class, args);
    }

}
