package com.abouerp.library.applet.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Abouerp
 */
@Data
public class WxLoginVO {

    private String gender;
    private String nickName;
    private String jsCode;

}
