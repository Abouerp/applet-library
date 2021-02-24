package com.abouerp.library.applet.controller;

import com.abouerp.library.applet.bean.ResultBean;
import com.abouerp.library.applet.service.CacheService;
import com.abouerp.library.applet.service.WxRequestService;
import org.springframework.web.bind.annotation.*;

/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/user")
public class AdministratorController {

    private final WxRequestService wxRequestService;
    private final CacheService cacheService;

    public AdministratorController(WxRequestService wxRequestService,
                                   CacheService cacheService) {
        this.wxRequestService = wxRequestService;
        this.cacheService = cacheService;
    }

    @PostMapping("/login")
    public ResultBean login(@RequestParam String code){
        cacheService.login(wxRequestService.login(code));
        //todo 生成token返回
        return ResultBean.ok();
    }
}
