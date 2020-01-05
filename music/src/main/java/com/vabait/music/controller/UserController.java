package com.vabait.music.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.vabait.music.ProxyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class UserController {
    @Autowired
    ProxyClient proxyClient;
    @Autowired
    RestTemplate restTemplate;
    String serverName = "user-center";

    @PostMapping(value = "login")
    public String run(@RequestParam("userName") String userName, @RequestParam("password") String password) {
        System.out.println("接收到 userName : " + userName + " password : " + password);
        return proxyClient.insert(userName, password);
    }

    //开启断路支持
    @HystrixCommand(fallbackMethod = "findByUserNameFallBack")
    @RequestMapping(value = "{userName}/findByUserName", method = RequestMethod.GET)
    public String rest2(@PathVariable String userName) {
        System.out.println("先执行我这里");
        Map<String, String> gson = new Gson().fromJson(proxyClient.findByUserName(userName), new TypeToken<Map<String, String>>() {
        }.getType());
        System.out.println("gson map : " + gson);
        return gson.toString();
    }

    public String findByUserNameFallBack(String userName) {
        return "非常抱歉 ： " + userName + " 服务器已经宕掉了 , 稍后再来看看吧";
    }
}
