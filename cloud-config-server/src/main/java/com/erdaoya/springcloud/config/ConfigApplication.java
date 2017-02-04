package com.erdaoya.springcloud.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 2017/1/13
 * @author erdaoya
 * @since 1.0
 */

@EnableConfigServer
@SpringBootApplication
@EnableDiscoveryClient
public class ConfigApplication {
    public static void main(String[] args) {
        long starTime = System.currentTimeMillis();
        SpringApplication.run(ConfigApplication.class, args);
        long endTime = System.currentTimeMillis();
        long time = endTime - starTime;
        System.out.println("\nStart Time: " + time / 1000 + " s");
        System.out.println("...............................................................");
        System.out.println("..................Service starts successfully..................");
        System.out.println("...............................................................");
    }
}
