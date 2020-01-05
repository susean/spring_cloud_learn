package com.vabait.music;

import com.vabait.music.config.FeignLogConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@FeignClient(value = "user-center", configuration = FeignLogConfiguration.class/*,fallback = FeignClientFallback.class*/)
public interface ProxyClient {

    @PostMapping(value = "/user/login")
    String insert(@RequestParam("userName") String userName, @RequestParam("password") String password);

    @RequestMapping(value = "user/{userName}/findByUserName", method = RequestMethod.GET)
    String findByUserName(@PathVariable String userName);
}
