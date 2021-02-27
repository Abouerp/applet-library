package com.abouerp.library.applet.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Abouerp
 */
@Component
@Data
@ConfigurationProperties(prefix = "wx.miniapp")
public class WxParamProperties {
    private String appid;
    private String secret;
}
