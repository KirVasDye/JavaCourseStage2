package org.example.lesson4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("org.example.lesson2.model")
public class Demo {
    public static void main(String[] args) {
        SpringApplication.run(Demo.class, args);
    }
}
