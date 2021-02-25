package com.abouerp.library.applet.dto;

import lombok.Data;

/**
 * @author Abouerp
 */
@Data
public class TokenDTO {

    private final String accessToken;
    private final Long expiresIn;

    public TokenDTO(String accessToken, Long expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }
}
