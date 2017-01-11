package com.erdaoya.springcloud.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 *
 *@author erdaoya
 *@data 2016/12/25 19:01
 */
@SpringBootApplication
@ImportResource("spring/spring-boot.xml")
@ComponentScan("com.erdaoya.springcloud")
@EnableDiscoveryClient
public class UserApplication {
    public static void main(String[] args) {
        long starTime = System.currentTimeMillis();
        SpringApplication.run(UserApplication.class,args);
        long endTime=System.currentTimeMillis();
        long Time=endTime-starTime;
        System.out.println("\nStart Time: "+ Time/1000 +" s");
        System.out.println("...............................................................");
        System.out.println("..................Service starts successfully..................");
        System.out.println("...............................................................");
    }
}
