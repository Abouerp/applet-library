package com.abouerp.library.applet.service;


import com.abouerp.library.applet.config.WxParamProperties;
import com.abouerp.library.applet.dto.WxLoginDTO;
import com.abouerp.library.applet.utils.JsonUtils;
import com.abouerp.library.applet.utils.WebClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;


/**
 * @author Abouerp
 */
@Slf4j
@Service
public class WxRequestService {

    private final static String GRANT_TYPE = "authorization_code";
    private final static String LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";
    private final WxParamProperties wxParamProperties;


    public WxRequestService(WxParamProperties wxParamProperties) {
        this.wxParamProperties = wxParamProperties;
    }

    private UriComponentsBuilder url(String rootPath) {
        return UriComponentsBuilder.fromHttpUrl(rootPath);
    }

    public WxLoginDTO login(String jsCode){
        UriComponentsBuilder uriComponentsBuilder = url(LOGIN_URL)
                .queryParam("appid", wxParamProperties.getAppid())
                .queryParam("secret",wxParamProperties.getSecret())
                .queryParam("js_code",jsCode)
                .queryParam("grant_type",GRANT_TYPE);

        log.info("url = {}",uriComponentsBuilder.toUriString());
        return WebClientUtils.DEFAULT_CLIENT.get()
                .uri(uriComponentsBuilder.toUriString())
                .retrieve()
                .bodyToMono(String.class)
                .map(it-> JsonUtils.readValue(it, WxLoginDTO.class))
                .block();
    }
}
