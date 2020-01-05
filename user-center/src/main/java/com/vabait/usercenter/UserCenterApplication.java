package com.vabait.usercenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableHystrixDashboard
@EnableDiscoveryClient
@SpringBootApplication
@EnableRabbit
@MapperScan(basePackages = ("com.vabait.usercenter.storage.entity"))
@ComponentScan({
        "com.vabait.usercenter.storage.service",
        "com.vabait.usercenter.storage.entity",
        "com.vabait.usercenter.api.controller",
        "com.vabait.usercenter.api.swagger",
        "com.vabait.usercenter.security",
        "com.vabait.usercenter.rabbitmq"})
public class UserCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserCenterApplication.class, args);
    }

}
