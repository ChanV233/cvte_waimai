package com.cvte.waimai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class WaimaiOrderProductor8001Application {
    public static void main(String[] args) {
        SpringApplication.run(WaimaiOrderProductor8001Application.class, args);
    }
}
