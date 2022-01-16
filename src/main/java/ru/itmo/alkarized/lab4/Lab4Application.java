package ru.itmo.alkarized.lab4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"ru.itmo.alkarized.lab4.configurations", "ru.itmo.alkarized.lab4.controllers",
        "ru.itmo.alkarized.lab4.entities", "ru.itmo.alkarized.lab4.services"})
public class Lab4Application {
    
    public static void main(String[] args) {
        SpringApplication.run(Lab4Application.class, args);
    }

}
