package com.cvte.waimai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class WaimaiEureka7001Application {
    public static void main(String[] args) {
        SpringApplication.run(WaimaiEureka7001Application.class, args);
    }
}
