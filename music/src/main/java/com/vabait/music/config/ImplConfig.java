package com.vabait.music.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ImplConfig {

    @Bean
    @LoadBalanced//对restTemplate进行负载均衡
    public RestTemplate restTemplate () {
        return new RestTemplate();
    }
}
