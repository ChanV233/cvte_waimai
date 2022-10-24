package com.cvte.waimai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import service.OrderProducerService;

@SpringBootApplication
@EnableEurekaClient
//@EnableFeignClients(basePackages = "com.cvte")
@EnableFeignClients(basePackageClasses = OrderProducerService.class)
public class WaimaiConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(WaimaiConsumerApplication.class, args);
    }
}
