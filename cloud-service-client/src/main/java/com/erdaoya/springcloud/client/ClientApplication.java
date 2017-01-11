package com.erdaoya.springcloud.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 *
 *@author erdaoya
 *@data 2016/12/25 21:25
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ClientApplication {
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        RestTemplate template = new RestTemplate();
        return template;

    }
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class,args);
    }
}
