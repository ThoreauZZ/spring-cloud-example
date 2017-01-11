package com.erdaoya.springcloud.gateway;

import com.erdaoya.springcloud.gateway.filter.AccessFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 *
 *@author erdaoya
 *@data 2016/12/25 21:25
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class GateWayApplication {
    public static void main(String[] args) {
        long starTime = System.currentTimeMillis();
        SpringApplication.run(GateWayApplication.class,args);
        long endTime=System.currentTimeMillis();
        long Time=endTime-starTime;
        System.out.println("\nStart Time: "+ Time/1000 +" s");
        System.out.println("...............................................................");
        System.out.println("..................Service starts successfully..................");
        System.out.println("...............................................................");
    }
    @Bean
    public AccessFilter accessFilter() {
        return new AccessFilter();
    }
}
