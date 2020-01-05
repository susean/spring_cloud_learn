package com.vabait.music.service;

import com.vabait.music.ProxyClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class ProxyClientIlpl implements ProxyClient {
    @Override
    public String insert(@RequestParam("userName") String userName, @RequestParam("password") String password) {
        return "不知道返回什么东西";
    }

    @Override
    public String findByUserName(String userName) {
        return null;
    }
}
