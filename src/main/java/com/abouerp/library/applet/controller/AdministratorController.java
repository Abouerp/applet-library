package com.abouerp.library.applet.controller;

import com.abouerp.library.applet.bean.ResultBean;
import com.abouerp.library.applet.service.WxRequestService;
import org.springframework.web.bind.annotation.*;

/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/user")
public class AdministratorController {

    private final WxRequestService wxRequestService;

    public AdministratorController(WxRequestService wxRequestService) {
        this.wxRequestService = wxRequestService;
    }

    @PostMapping("/login")
    public ResultBean login(@RequestParam String code){
        return ResultBean.ok(wxRequestService.login(code));
    }
}
