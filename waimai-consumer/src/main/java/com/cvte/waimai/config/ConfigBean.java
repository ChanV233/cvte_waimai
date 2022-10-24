package com.cvte.waimai.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.WeightedResponseTimeRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author ChanV
 * @create 2021-02-04-22:02
 */
@Configuration
public class ConfigBean {

    @Bean
    @LoadBalanced   //Ribbon
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @Bean
    public IRule ribbonRule() {
        return new WeightedResponseTimeRule();
    }
}
