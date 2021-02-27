package com.abouerp.library.applet.service;

import com.abouerp.library.applet.domain.Administrator;
import com.abouerp.library.applet.domain.Role;
import com.abouerp.library.applet.dto.TokenDTO;
import com.abouerp.library.applet.dto.WxLoginDTO;
import com.abouerp.library.applet.exception.BackstageConfigException;
import com.abouerp.library.applet.exception.BadRequestException;
import com.abouerp.library.applet.repository.AdministratorRepository;
import com.abouerp.library.applet.repository.RoleRepository;
import com.abouerp.library.applet.utils.JsonUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Abouerp
 */
@Service
public class CacheService {

    private final RedisTemplate redisTemplate;
    private final AdministratorRepository administratorRepository;
    private final RoleRepository roleRepository;
    private final String TOKEN = "ACCESSTOKEN";
    // token：json(user)  的形式

    public CacheService(RedisTemplate redisTemplate,
                        AdministratorRepository administratorRepository,
                        RoleRepository roleRepository) {
        this.redisTemplate = redisTemplate;
        this.administratorRepository = administratorRepository;
        this.roleRepository = roleRepository;
    }

//    public TokenDTO login(WxLoginDTO loginDTO) {
//        if (loginDTO != null && loginDTO.getOpenId() != null) {
//            Administrator administrator = administratorRepository.findByOpenId(loginDTO.getOpenId()).orElse(null);
//            if (administrator == null) {
//                //todo 还需要username 以及设置初始密码
//                administrator.setOpenId(loginDTO.getOpenId());
//                Role role = roleRepository.findFirstByIsDefault(true).orElseThrow(BackstageConfigException::new);
//                Set<Role> roles = new HashSet<>();
//                roles.add(role);
//                administrator.setRoles(roles);
//                administrator = administratorRepository.save(administrator);
//            }
//            //生成token
//            String redisKey = RandomStringUtils.randomAlphanumeric(64);
//            String redisValue = JsonUtils.writeValueAsString(administrator);
//            redisTemplate.opsForValue().set(redisKey, redisValue, 30L, TimeUnit.MINUTES);
//            return new TokenDTO(redisKey,30*60L);
//        }
//        throw new BadRequestException();
//    }
}
