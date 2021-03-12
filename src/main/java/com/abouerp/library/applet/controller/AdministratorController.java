package com.abouerp.library.applet.controller;

import com.abouerp.library.applet.bean.ResultBean;
import com.abouerp.library.applet.domain.Administrator;
import com.abouerp.library.applet.domain.book.RecordStatus;
import com.abouerp.library.applet.exception.UnauthorizedException;
import com.abouerp.library.applet.mapper.AdministratorMapper;
import com.abouerp.library.applet.security.UserPrincipal;
import com.abouerp.library.applet.service.AdministratorService;
import com.abouerp.library.applet.service.BookRecordService;
import com.abouerp.library.applet.utils.JsonUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final RedisTemplate<String, String> redisTemplate;
    private final BookRecordService bookRecordService;


    public AdministratorController(AdministratorService administratorService,
                                   RedisTemplate<String, String> redisTemplate,
                                   BookRecordService bookRecordService) {
        this.administratorService = administratorService;
        this.redisTemplate = redisTemplate;
        this.bookRecordService = bookRecordService;
    }

    @GetMapping("/me")
    public ResultBean<Map<String, Object>> me(HttpServletRequest request) {
        //todo enum define
        String token = request.getHeader("token");
        if (token == null) {
            throw new UnauthorizedException();
        }
        String json = redisTemplate.opsForValue().get(token);
        Map<String, Object> map = new HashMap<>(2);
        if (json != null) {
            Administrator administrator = JsonUtils.readValue(json, Administrator.class);
            map.put("user", AdministratorMapper.INSTANCE.toDTO(administrator));
        }
        return ResultBean.ok(map);
    }

    //获取个人借阅记录
    @GetMapping("/book/record")
    public ResultBean getRecord(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                RecordStatus recordStatus,
                                @PageableDefault(sort = {"status","updateTime"},direction = Sort.Direction.DESC) Pageable pageable) {
        return ResultBean.ok(bookRecordService.findByUserId(userPrincipal.getId(),pageable,recordStatus));
    }

    @GetMapping
    public ResultBean getAll() {
        return ResultBean.ok(RecordStatus.mappers);
    }

}
