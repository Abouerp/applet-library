package com.abouerp.library.applet.service;

import com.abouerp.library.applet.domain.Administrator;
import com.abouerp.library.applet.dto.LoginDTO;
import com.abouerp.library.applet.repository.AdministratorRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Abouerp
 */
@Service
public class CacheService {

    private final RedisTemplate redisTemplate;
    private final AdministratorRepository administratorRepository;

    public CacheService(RedisTemplate redisTemplate,
                        AdministratorRepository administratorRepository) {
        this.redisTemplate = redisTemplate;
        this.administratorRepository = administratorRepository;
    }

    public void login(LoginDTO loginDTO) {
        //todo 处理登陆用户存储redis
        if (loginDTO != null && loginDTO.getOpenId() != null){
            Administrator administrator = administratorRepository.findByOpenId(loginDTO.getOpenId()).orElse(null);
            if (administrator == null){
                //todo 注册用户
            }else {

            }
        }
    }
}
