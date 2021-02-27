package com.abouerp.library.applet.controller;

import com.abouerp.library.applet.bean.ResultBean;
import com.abouerp.library.applet.domain.Administrator;
import com.abouerp.library.applet.mapper.AdministratorMapper;
import com.abouerp.library.applet.service.AdministratorService;
import com.abouerp.library.applet.utils.JsonUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/user")
public class AdministratorController {

    private final AdministratorService administratorService;
    private final RedisTemplate<String,String> redisTemplate;

    public AdministratorController(AdministratorService administratorService,
                                   RedisTemplate<String,String> redisTemplate) {
        this.administratorService = administratorService;
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/me")
    public ResultBean<Map<String, Object>> me(HttpServletRequest request) {
        String token = request.getHeader("token");
        Administrator administrator = JsonUtils.readValue(redisTemplate.opsForValue().get(token), Administrator.class);
        Map<String, Object> map = new HashMap<>(2);
        map.put("user", AdministratorMapper.INSTANCE.toDTO(administrator));
        return ResultBean.ok(map);
    }
}
