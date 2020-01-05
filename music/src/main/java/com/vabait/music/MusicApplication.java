package com.vabait.music;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
//@EnableDiscoveryClient
@EnableFeignClients
//上述两者区别,详情见34页,各有各的特性
@EnableCircuitBreaker//启用短路支持
//@EnableHystrix//启用短路支持
public class MusicApplication {


    public static void main(String[] args) {
        SpringApplication.run(MusicApplication.class, args);
    }

}
